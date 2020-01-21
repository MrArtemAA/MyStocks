package ru.artemaa.stocks.web.operation;

import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import ru.artemaa.stocks.entity.Operation;
import ru.artemaa.stocks.entity.OperationType;
import ru.artemaa.stocks.entity.Stock;
import ru.artemaa.stocks.entity.portfolio.Portfolio;
import ru.artemaa.stocks.service.SummaryService;

import javax.inject.Inject;
import javax.inject.Named;

import static java.util.Objects.nonNull;
import static ru.artemaa.stocks.entity.OperationType.Dividends;

public class OperationEdit extends AbstractEditor<Operation> {
    @Inject
    private SummaryService summaryService;

    @Named("fieldGroup.portfolio")
    private PickerField<Portfolio> portfolioPicker;
    @Named("fieldGroup.stock")
    private PickerField<Stock> stockPicker;
    @Named("fieldGroup.type")
    private LookupField<OperationType> operationType;
    @Named("fieldGroup.amount")
    private TextField<Integer> amount;
    @Named("fieldGroup.price")
    private TextField<Double> price;
    @Inject
    private TextField<Double> pricePerOne;

    @Override
    protected void postInit() {
        super.postInit();

        updatePricePerOne(price.getValue(), amount.getValue());

        if (PersistenceHelper.isNew(getItem())) {
            operationType.addValueChangeListener(e -> {
                OperationType type = (OperationType) e.getValue();
                if (isPortfolioAndStockSet() && type == Dividends) {
                    updateAmountForDividends();
                }
            });
        }

        amount.addValueChangeListener(e -> updatePricePerOne(price.getValue(), (Integer) e.getValue()));
        price.addValueChangeListener(e -> updatePricePerOne((Double) e.getValue(), amount.getValue()));
    }

    private boolean isPortfolioAndStockSet() {
        Object portfolio = portfolioPicker.getValue();
        Stock stock = stockPicker.getValue();
        return nonNull(portfolio) &&  nonNull(stock);
    }

    private void updateAmountForDividends() {
        Object portfolio = portfolioPicker.getValue();
        Stock stock = stockPicker.getValue();
        summaryService.getStockSummary(stock.getId()).stream()
                .filter(stockSummary -> portfolio.equals(stockSummary.getPortfolio()))
                .findFirst()
                .ifPresent(stockSummary -> amount.setValue(stockSummary.getAmount()));
    }

    private void updatePricePerOne(Double price, Integer amount) {
        if (nonNull(price) && nonNull(amount)) {
            pricePerOne.setValue(price / amount);
        }
    }
}