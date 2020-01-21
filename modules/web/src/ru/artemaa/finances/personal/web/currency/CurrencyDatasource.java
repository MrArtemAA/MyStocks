package ru.artemaa.finances.personal.web.currency;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import ru.artemaa.finances.personal.entity.Currency;
import ru.artemaa.finances.personal.service.CurrencyService;

import java.util.Collection;
import java.util.Map;

/**
 * @author Artem Areshko
 *         27.07.2017
 */
public class CurrencyDatasource extends CustomCollectionDatasource<Currency, String> {

    private CurrencyService summaryService = AppBeans.get(CurrencyService.class);

    @Override
    protected Collection<Currency> getEntities(Map<String, Object> params) {
        return summaryService.getAll();
    }
}
