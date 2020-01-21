package ru.artemaa.finances.personal.service;

import com.haulmont.cuba.core.global.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.artemaa.finances.personal.config.StocksConfig;
import ru.artemaa.finances.personal.entity.Currency;

import javax.inject.Inject;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.math.BigDecimal;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.valueOf;
import static java.math.MathContext.DECIMAL64;
import static java.net.CookiePolicy.ACCEPT_ALL;
import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service(CurrencyService.NAME)
public class CurrencyServiceBean implements CurrencyService {
    private static final Logger log = LoggerFactory.getLogger(CurrencyServiceBean.class);

    private static final String BASE_CURRENCY_CODE = "RUB";

    private static final String BASE_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final DateTimeFormatter DATE_FORMATTER = ofPattern(DATE_FORMAT);
    private static final String DATE_URL_FORMAT = BASE_URL + "?date_req=%s";

    private final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    private final StocksConfig stocksConfig;
    private final Metadata metadata;

    private volatile boolean loaded = false;
    private LocalDate date = now();
    private final Map<String, ExchangeRate> rates = new ConcurrentHashMap<>();

    @Inject
    public CurrencyServiceBean(StocksConfig stocksConfig, Metadata metadata) {
        this.stocksConfig = stocksConfig;
        this.metadata = metadata;

        saxParserFactory.setNamespaceAware(false);
        saxParserFactory.setValidating(false);
    }

    @Override
    public Collection<Currency> getAll() {
        String[] currencyCodes = stocksConfig.getCurrencyCodes().split(",");
        Collection<Currency> result = new HashSet<>();
        for (String code : currencyCodes) {
            try{
                result.add(get(code));
            } catch (IllegalArgumentException ignored) {}
        }
        return result;
    }

    @Override
    public Currency get(String code) {
        java.util.Currency javaCurrency = java.util.Currency.getInstance(code.toUpperCase());
        Currency currency = metadata.create(Currency.class);
        currency.setId(javaCurrency.getCurrencyCode());
        currency.setCode(javaCurrency.getCurrencyCode());
        currency.setName(javaCurrency.getDisplayName(Locale.getDefault()));
        return currency;
    }

    @Override
    public BigDecimal convert(Currency sourceCurrency, Currency targetCurrency, BigDecimal amount) {
        loadRates();

        ExchangeRate exchangeRate = getExchangeRate(sourceCurrency, targetCurrency);
        log.info("Converting {} {} to {} with rate {}", amount, sourceCurrency.getCode(), targetCurrency.getCode(), exchangeRate.factor);
        return amount.multiply(exchangeRate.factor);
    }

    private void loadRates() {
        if (!(loaded && date.equals(now()))) {
            synchronized (this) {
                if (!(loaded && date.equals(now()))) {
                    log.info("Attempting to load rates...");
                    try {
                        date = now();
                        CookieHandler.setDefault(new CookieManager(null, ACCEPT_ALL));
                        URL url = new URL(String.format(DATE_URL_FORMAT, date.format(DATE_FORMATTER)));
                        URLConnection urlConnection = url.openConnection();
                        SAXParser parser = saxParserFactory.newSAXParser();
                        parser.parse(urlConnection.getInputStream(), new RateHandler(rates));
                        loaded = true;
                        log.info("Rates loaded.");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private ExchangeRate getExchangeRate(Currency sourceCurrency, Currency targetCurrency) {
        ExchangeRate sourceRate = rates.get(sourceCurrency.getCode());
        ExchangeRate targetRate = rates.get(targetCurrency.getCode());

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.baseCurrency = sourceCurrency;
        exchangeRate.currency = targetCurrency;

        if (areBothBaseCurrencies(sourceCurrency, targetCurrency)) {
            exchangeRate.factor = ONE;
            return exchangeRate;
        } else if (BASE_CURRENCY_CODE.equals(targetCurrency.getCode())) {
            if (isNull(sourceRate)) {
                return null;
            }
            return reverse(sourceRate);
        } else if (BASE_CURRENCY_CODE.equals(sourceCurrency.getCode())) {
            return targetRate;
        } else {
            if (nonNull(sourceRate) && nonNull(targetRate)) {
                exchangeRate.factor = targetRate.factor.divide(sourceRate.factor, DECIMAL64);
            }
            return exchangeRate;
        }
    }

    private boolean areBothBaseCurrencies(Currency sourceCurrency, Currency targetCurrency) {
        return BASE_CURRENCY_CODE.equals(sourceCurrency.getCode()) &&
                BASE_CURRENCY_CODE.equals(targetCurrency.getCode());
    }

    private ExchangeRate reverse(ExchangeRate rate) {
        if (isNull(rate)) {
            throw new IllegalArgumentException("Rate null is not reversible.");
        }

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.baseCurrency = rate.currency;
        exchangeRate.currency = rate.baseCurrency;
        exchangeRate.factor = ONE.divide(rate.factor, DECIMAL64);

        return exchangeRate;
    }

    private class ExchangeRate {
        Currency baseCurrency;
        Currency currency;
        BigDecimal factor;

        @Override
        public String toString() {
            return "ExchangeRate{" +
                    "baseCurrency=" + baseCurrency +
                    ", currency=" + currency +
                    ", factor=" + factor +
                    '}';
        }
    }

    private class RateHandler extends DefaultHandler {
        //private static final String VAL_CURS = "ValCurs";
        private static final String VALUTE = "Valute";
        private static final String CHAR_CODE = "CharCode";
        private static final String NOMINAL = "Nominal";
        private static final String VALUE = "Value";

        private final Currency BASE_CURRENCY = get(BASE_CURRENCY_CODE);

        private final Map<String, ExchangeRate> rates;

        private class Valute {
            String charCode;
            Integer nominal;
            Double value;
        }

        private String currentElement;
        private Valute valute;

        RateHandler(Map<String, ExchangeRate> rates) {
            this.rates = rates;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (VALUTE.equals(qName)) {
                valute = new Valute();
            } else if (valute != null) {
                currentElement = qName;
            }
            super.startElement(uri, localName, qName, attributes);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            if (nonNull(currentElement)) {
                switch (currentElement) {
                    case CHAR_CODE: {
                        valute.charCode = String.valueOf(ch, start, length);
                        break;
                    }
                    case NOMINAL: {
                        valute.nominal = Integer.parseInt(String.valueOf(ch, start, length));
                        break;
                    }
                    case VALUE: {
                        valute.value = Double.parseDouble(String.valueOf(ch, start, length).replace(",", "."));
                        break;
                    }
                }
            }
            super.characters(ch, start, length);
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentElement = "";
            if (VALUTE.equals(qName)) {
                addRate();
                valute = null;
            }
            super.endElement(uri, localName, qName);
        }

        void addRate() {
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.baseCurrency = BASE_CURRENCY;
            exchangeRate.currency = get(valute.charCode);
            exchangeRate.factor = valueOf(valute.nominal / valute.value);
            rates.put(valute.charCode, exchangeRate);
        }

    }
}