package ru.artemaa.finances.personal.web.stocksummary;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomGroupDatasource;
import ru.artemaa.finances.personal.entity.StockSummary;
import ru.artemaa.finances.personal.service.SummaryService;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Artem Areshko
 *         27.07.2017
 */
public class StockSummaryCollectionDatasource extends CustomGroupDatasource<StockSummary, UUID> {

    //@Inject
    private SummaryService summaryService = AppBeans.get(SummaryService.class);

    @Override
    protected Collection<StockSummary> getEntities(Map<String, Object> params) {
        Objects.requireNonNull(summaryService, "Summary service can't be null");
        return summaryService.getStockSummaries();
    }
}
