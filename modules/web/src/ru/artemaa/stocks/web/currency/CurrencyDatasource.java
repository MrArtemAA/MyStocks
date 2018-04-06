package ru.artemaa.stocks.web.currency;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CollectionDatasourceImpl;
import ru.artemaa.stocks.entity.Currency;
import ru.artemaa.stocks.service.CurrencyService;

import java.util.Map;
import java.util.Objects;

/**
 * @author Artem Areshko
 *         27.07.2017
 */
public class CurrencyDatasource extends CollectionDatasourceImpl<Currency, String> {

    private CurrencyService summaryService = AppBeans.get(CurrencyService.class);

    @Override
    protected void loadData(Map<String, Object> params) {
        data.clear();

        dataLoadError = null;
        try {
            Objects.requireNonNull(summaryService, "Currency service can't be null");
            summaryService.getAll().forEach(currency -> data.put(currency.getId(), currency));
        } catch (Throwable e) {
            dataLoadError = e;
        }
    }
}
