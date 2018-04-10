package ru.artemaa.stocks.web.operation;

import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import ru.artemaa.stocks.entity.Operation;
import ru.artemaa.stocks.entity.OperationType;
import ru.artemaa.stocks.entity.Stock;
import ru.artemaa.stocks.service.StockSummaryService;

import javax.inject.Inject;
import javax.inject.Named;

import static ru.artemaa.stocks.entity.OperationType.Dividends;

public class OperationEdit extends AbstractEditor<Operation> {
    @Inject
    private StockSummaryService stockSummaryService;

    @Named("fieldGroup.portfolio")
    private PickerField portfolioPicker;
    @Named("fieldGroup.stock")
    private PickerField stockPicker;
    @Named("fieldGroup.type")
    private LookupField operationType;
    @Named("fieldGroup.amount")
    private TextField amount;

    @Override
    protected void postInit() {
        super.postInit();

        if (PersistenceHelper.isNew(getItem())) {
            operationType.addValueChangeListener(e -> {
                OperationType value = (OperationType) e.getValue();
                Object portfolio = portfolioPicker.getValue();
                Stock stock = stockPicker.getValue();
                if (portfolio != null && stock != null && value == Dividends) {
                    stockSummaryService.getStockSummary(stock.getId()).stream()
                            .filter(stockSummary -> portfolio.equals(stockSummary.getPortfolio()))
                            .findFirst()
                            .ifPresent(stockSummary -> amount.setValue(stockSummary.getAmount()));
                }
            });
        }
    }
}