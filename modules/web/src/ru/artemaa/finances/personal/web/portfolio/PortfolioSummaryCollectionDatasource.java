package ru.artemaa.finances.personal.web.portfolio;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import ru.artemaa.finances.personal.entity.portfolio.Portfolio;
import ru.artemaa.finances.personal.entity.portfolio.PortfolioSummary;
import ru.artemaa.finances.personal.service.SummaryService;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;


public class PortfolioSummaryCollectionDatasource extends CustomCollectionDatasource<PortfolioSummary, UUID> {

    private SummaryService summaryService = AppBeans.get(SummaryService.class);

    @Override
    protected Collection<PortfolioSummary> getEntities(Map<String, Object> params) {
        Portfolio portfolio = (Portfolio) params.get("portfolio");
        if (isNull(portfolio)) {
            UUID portfolioId = (UUID) params.get("portfolioId");
            if (isNull(portfolioId)) {
                throw new IllegalArgumentException();
            } else {
                return summaryService.getPortfolioSummaries(portfolioId);
            }
        } else {
            return summaryService.getPortfolioSummaries(portfolio);
        }
    }
}