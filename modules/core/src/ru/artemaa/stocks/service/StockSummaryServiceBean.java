package ru.artemaa.stocks.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.artemaa.stocks.entity.Operation;
import ru.artemaa.stocks.entity.Stock;
import ru.artemaa.stocks.entity.StockSummary;
import ru.artemaa.stocks.entity.portfolio.Portfolio;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service(StockSummaryService.NAME)
public class StockSummaryServiceBean implements StockSummaryService {
    private static final Logger LOG = getLogger(StockSummaryServiceBean.class);

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    @Override
    public List<StockSummary> getStockSummary(final UUID stockId) {
        Stock stock = dataManager.load(LoadContext.create(Stock.class).setId(stockId));
        return getStockSummary(stock);
    }

    @Override
    public List<StockSummary> getStockSummaries() {
        LoadContext.Query query = LoadContext.createQuery("select s from stocks$Stock s");

        List<Stock> stocks = dataManager.loadList(
                LoadContext.create(Stock.class)
                .setQuery(query)
        );

        return stocks.stream()
                .flatMap(stock -> getStockSummary(stock).stream())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<StockSummary> getStockSummary(final Stock stock) {
        List<Operation> operations = getOperations(stock);
        if (operations.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Portfolio, List<Operation>> operationsByPortfolio = operations.stream()
                .collect(Collectors.groupingBy(Operation::getPortfolio, Collectors.toList()));

        return operationsByPortfolio.entrySet().stream()
                .map(portfolioListEntry -> getPortfolioSummary(portfolioListEntry.getKey(), stock, portfolioListEntry.getValue()))
                .collect(Collectors.toList());
    }

    private StockSummary getPortfolioSummary(Portfolio portfolio, Stock stock, List<Operation> operations) {
        StockSummary stockSummary = metadata.create(StockSummary.class);

        stockSummary.setPortfolio(portfolio);
        stockSummary.setStock(stock);

        AtomicInteger totalDividendsAmount = new AtomicInteger(0);
        AtomicInteger totalPurchasedAmount = new AtomicInteger(0);
        AtomicInteger totalSoldAmount = new AtomicInteger(0);

        operations.forEach(operation -> {
            LOG.info(operation.toString());
            switch (operation.getType()) {
                case Purchase:
                    stockSummary.setAmount(stockSummary.getAmount() + operation.getAmount());
                    totalPurchasedAmount.addAndGet(operation.getAmount());
                    stockSummary.setTotalPurchasePrice(stockSummary.getTotalPurchasePrice() + operation.getPrice());
                    break;
                case Sale:
                    stockSummary.setAmount(stockSummary.getAmount() - operation.getAmount());
                    totalSoldAmount.addAndGet(operation.getAmount());
                    stockSummary.setTotalSellPrice(stockSummary.getTotalSellPrice() + operation.getPrice());
                    break;
                case Dividends:
                    stockSummary.setTotalDividends(stockSummary.getTotalDividends() + operation.getPrice());
                    totalDividendsAmount.getAndAdd(operation.getAmount());
                    break;
            }
        });

        stockSummary.setAvgPurchasePrice(stockSummary.getTotalPurchasePrice() / totalPurchasedAmount.get());
        stockSummary.setAvgSellPrice(stockSummary.getTotalSellPrice() / totalSoldAmount.get());
        stockSummary.setAvgDividends(stockSummary.getTotalDividends() / totalDividendsAmount.get());
        stockSummary.setPriceDividendsRatio(stockSummary.getAvgDividends() / stockSummary.getAvgPurchasePrice() * 100);

        return stockSummary;
    }

    private List<Operation> getOperations(Stock stock) {
        LoadContext.Query query = LoadContext.createQuery("select o from stocks$Operation o where " +
                "o.stock.id = :stockId")
                .setParameter("stockId", stock.getId());

        return dataManager.loadList(
                LoadContext.create(Operation.class)
                        .setQuery(query)
                        .setView("operation-detailed-view")
        );
    }
}