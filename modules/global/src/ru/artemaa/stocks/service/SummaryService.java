package ru.artemaa.stocks.service;


import ru.artemaa.stocks.entity.StockSummary;
import ru.artemaa.stocks.entity.portfolio.Portfolio;
import ru.artemaa.stocks.entity.portfolio.PortfolioSummary;

import java.util.List;
import java.util.UUID;

public interface SummaryService {
    String NAME = "stocks_StockSummaryService";

    List<StockSummary> getStockSummary(UUID stockId);
    List<StockSummary> getStockSummaries();
    List<PortfolioSummary> getPortfolioSummaries(Portfolio portfolio);
    List<PortfolioSummary> getPortfolioSummaries(UUID portfolioId);

}