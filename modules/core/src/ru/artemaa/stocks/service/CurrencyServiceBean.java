package ru.artemaa.stocks.service;

import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;
import ru.artemaa.stocks.config.StocksConfig;
import ru.artemaa.stocks.entity.Currency;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;

@Service(CurrencyService.NAME)
public class CurrencyServiceBean implements CurrencyService {
    @Inject
    private StocksConfig stocksConfig;
    @Inject
    private Metadata metadata;

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
    public Currency getMain() {
        return get(stocksConfig.getMainCurrencyCode());
    }
}