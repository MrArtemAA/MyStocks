package ru.artemaa.stocks.web.screens;

import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.artemaa.stocks.entity.Account;
import ru.artemaa.stocks.entity.Currency;
import ru.artemaa.stocks.service.CurrencyService;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class Balance extends AbstractWindow {
    @Inject
    private CurrencyService currencyService;

    @Inject
    private CollectionDatasource<Account, UUID> accountsDs;
    @Inject
    private TextField total;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        Currency mainCurrency = currencyService.getMain();
        /*total.setValue(accountsDs.getItems().stream()
                .collect(Collectors.summingDouble(account -> {
                    if (account.getCurrencyCode().equals(mainCurrency.getCode())) {
                        return account.getAmount();
                    } else {
                        return account.getAmount().multiply(new BigDecimal(10));
                    }
                })));*/
    }
}