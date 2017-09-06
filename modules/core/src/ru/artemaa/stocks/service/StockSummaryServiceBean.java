package ru.artemaa.stocks.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UserSessionSource;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.artemaa.stocks.entity.Operation;
import ru.artemaa.stocks.entity.Stock;
import ru.artemaa.stocks.entity.StockSummary;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service(StockSummaryService.NAME)
public class StockSummaryServiceBean implements StockSummaryService {
    private static final Logger LOG = getLogger(StockSummaryServiceBean.class);

    @Inject
    private DataManager dataManager;

    @Inject
    private UserSessionSource userSessionSource;

    @Override
    public StockSummary getStockSummary(final UUID stockId) {
        Stock stock = dataManager.load(
                LoadContext.create(Stock.class)
                        .setId(stockId));

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
                .map(this::getStockSummary)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private StockSummary getStockSummary(final Stock stock) {
        LoadContext.Query query = LoadContext.createQuery("select o from stocks$Operation o where " +
                "o.stock.id = :stockId and o.createdBy = :userLogin")
                .setParameter("stockId", stock.getId())
                .setParameter("userLogin", userSessionSource.getUserSession().getCurrentOrSubstitutedUser().getLogin());

        List<Operation> operations = dataManager.loadList(
                LoadContext.create(Operation.class)
                        .setQuery(query)
        );

        if (operations.isEmpty()) return null;

        AtomicInteger totalDividendsAmount = new AtomicInteger(0);
        AtomicInteger totalPurchasedAmount = new AtomicInteger(0);
        AtomicInteger totalSoldAmount = new AtomicInteger(0);
        StockSummary summary = new StockSummary();
        summary.setStock(stock);

        operations.forEach(operation -> {
            Objects.requireNonNull(summary);
            LOG.info(operation.toString());
            switch (operation.getType()) {
                case Purchase:
                    summary.setAmount(summary.getAmount() + operation.getAmount());
                    totalPurchasedAmount.addAndGet(operation.getAmount());
                    summary.setTotalPurchasePrice(summary.getTotalPurchasePrice() + operation.getPrice());
                    break;
                case Sale:
                    summary.setAmount(summary.getAmount() - operation.getAmount());
                    totalSoldAmount.addAndGet(operation.getAmount());
                    summary.setTotalSellPrice(summary.getTotalSellPrice() + operation.getPrice());
                    break;
                case Dividends:
                    summary.setTotalDividends(summary.getTotalDividends() + operation.getPrice());
                    totalDividendsAmount.getAndAdd(operation.getAmount());
                    break;
            }
        });

        summary.setAvgPurchasePrice(summary.getTotalPurchasePrice() / totalPurchasedAmount.get());
        summary.setAvgSellPrice(summary.getTotalSellPrice() / totalSoldAmount.get());
        summary.setAvgDividends(summary.getTotalDividends() / totalDividendsAmount.get());
        summary.setPriceDividendsRatio(summary.getAvgDividends() / summary.getAvgPurchasePrice() * 100);

        return summary;
    }
}