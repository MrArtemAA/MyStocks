package ru.artemaa.finances.personal.service;


import ru.artemaa.finances.personal.entity.StockSummary;
import ru.artemaa.finances.personal.entity.portfolio.Portfolio;
import ru.artemaa.finances.personal.entity.portfolio.PortfolioSummary;

import java.util.List;
import java.util.UUID;

public interface SummaryService {
    String NAME = "stocks_StockSummaryService";

    List<StockSummary> getStockSummary(UUID stockId);
    List<StockSummary> getStockSummaries();
    List<PortfolioSummary> getPortfolioSummaries(Portfolio portfolio);
    List<PortfolioSummary> getPortfolioSummaries(UUID portfolioId);

}