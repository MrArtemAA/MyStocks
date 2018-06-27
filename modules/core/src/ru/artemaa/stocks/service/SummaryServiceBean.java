package ru.artemaa.stocks.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.artemaa.stocks.entity.Currency;
import ru.artemaa.stocks.entity.Operation;
import ru.artemaa.stocks.entity.Stock;
import ru.artemaa.stocks.entity.StockSummary;
import ru.artemaa.stocks.entity.portfolio.Portfolio;
import ru.artemaa.stocks.entity.portfolio.PortfolioSummary;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static java.math.BigDecimal.valueOf;
import static org.slf4j.LoggerFactory.getLogger;

@Service(SummaryService.NAME)
public class SummaryServiceBean implements SummaryService {
    private static final Logger LOG = getLogger(SummaryServiceBean.class);

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
        StockSummaryBuilder stockSummaryBuilder = new StockSummaryBuilder(portfolio, stock);
        operations.forEach(stockSummaryBuilder::processOperation);
        return stockSummaryBuilder.build();
    }

    private abstract class SummaryBuilder<T> {
        protected T summary;

        SummaryBuilder(Class<T> summaryType) {
            summary = metadata.create(summaryType);
        }

        public final void processOperation(Operation operation) {
            LOG.info(operation.toString());
            switch (operation.getType()) {
                case Purchase:
                    doProcessPurchase(operation);
                    break;
                case Sale:
                    doProcessSale(operation);
                    break;
                case Dividends:
                    doProcessDividends(operation);
                    break;
            }
        }

        protected abstract void doProcessPurchase(Operation operation);
        protected abstract void doProcessSale(Operation operation);
        protected abstract void doProcessDividends(Operation operation);

        public T build() {
            return summary;
        }
    }

    private class StockSummaryBuilder extends SummaryBuilder<StockSummary> {
        Integer totalDividendsAmount = 0;
        Integer totalPurchasedAmount = 0;
        Integer totalSoldAmount = 0;

        StockSummaryBuilder(Portfolio portfolio, Stock stock) {
            super(StockSummary.class);
            summary.setPortfolio(portfolio);
            summary.setStock(stock);
        }

        @Override
        protected void doProcessPurchase(Operation operation) {
            summary.setAmount(summary.getAmount() + operation.getAmount());
            totalPurchasedAmount += operation.getAmount();
            summary.setTotalPurchasePrice(summary.getTotalPurchasePrice() + operation.getPrice());
        }

        @Override
        protected void doProcessSale(Operation operation) {
            summary.setAmount(summary.getAmount() - operation.getAmount());
            totalSoldAmount += operation.getAmount();
            summary.setTotalSellPrice(summary.getTotalSellPrice() + operation.getPrice());
        }

        @Override
        protected void doProcessDividends(Operation operation) {
            summary.setTotalDividends(summary.getTotalDividends() + operation.getPrice());
            totalDividendsAmount += operation.getAmount();
        }

        public StockSummary build() {
            summary.setAvgPurchasePrice(divide(summary.getTotalPurchasePrice(), totalPurchasedAmount));
            summary.setAvgSellPrice(divide(summary.getTotalSellPrice(), totalSoldAmount));
            summary.setAvgDividends(divide(summary.getTotalDividends(), totalDividendsAmount));
            summary.setPriceDividendsRatio(summary.getAvgDividends()
                    .divide(summary.getAvgPurchasePrice())
                    .multiply(valueOf(100))
            );

            return summary;
        }

        private BigDecimal divide(Double number1, Integer number2) {
            return number2 == 0 ? valueOf(0.0) : valueOf(number1 / number2);
        }
    }

    @Override
    public List<PortfolioSummary> getPortfolioSummaries(UUID portfolioId) {
        Portfolio portfolio = dataManager.load(Portfolio.class).id(portfolioId).one();
        return getPortfolioSummaries(portfolio);
    }

    @Override
    public List<PortfolioSummary> getPortfolioSummaries(Portfolio portfolio) {
        List<Operation> operations = getOperations(portfolio);

        return operations.stream()
                .collect(Collectors.groupingBy(operation -> operation.getStock().getCurrency(), Collectors.toList()))
                .entrySet().stream()
                .map(mapEntry -> getPortfolioSummary(portfolio, mapEntry.getKey(), mapEntry.getValue()))
                .collect(Collectors.toList());
    }

    private PortfolioSummary getPortfolioSummary(Portfolio portfolio, Currency currency, List<Operation> operations) {
        PortfolioSummaryBuilder portfolioSummaryBuilder = new PortfolioSummaryBuilder(portfolio, currency);
        operations.forEach(portfolioSummaryBuilder::processOperation);
        return portfolioSummaryBuilder.build();
    }

    private class PortfolioSummaryBuilder extends SummaryBuilder<PortfolioSummary> {
        PortfolioSummaryBuilder(Portfolio portfolio, Currency currency) {
            super(PortfolioSummary.class);
            summary.setPortfolio(portfolio);
            summary.setCurrency(currency);
        }

        @Override
        protected void doProcessPurchase(Operation operation) {
            summary.setSum(summary.getSum().add(valueOf(operation.getPrice())));
        }

        @Override
        protected void doProcessSale(Operation operation) {
            summary.setSum(summary.getSum().subtract(valueOf(operation.getPrice())));
        }

        @Override
        protected void doProcessDividends(Operation operation) {}

        @Override
        public PortfolioSummary build() {

            return super.build();
        }
    }

    private List<Operation> getOperations(Portfolio portfolio) {
        return dataManager.load(Operation.class)
                .query("select o from stocks$Operation o where o.portfolio.id = :portfolioId")
                .parameter("portfolioId", portfolio.getId())
                .view("operation-detailed-view")
                .list();
    }

    private List<Operation> getOperations(Stock stock) {
        return dataManager.load(Operation.class)
                .query("select o from stocks$Operation o where o.stock.id = :stockId")
                .parameter("stockId", stock.getId())
                .view("operation-detailed-view")
                .list();
    }
}