package ru.artemaa.stocks.web.operation;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.components.TextField;
import ru.artemaa.stocks.entity.Operation;
import ru.artemaa.stocks.entity.OperationType;
import ru.artemaa.stocks.entity.Stock;
import ru.artemaa.stocks.entity.StockSummary;
import ru.artemaa.stocks.service.StockSummaryService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

import static ru.artemaa.stocks.entity.OperationType.Dividends;

public class OperationEdit extends AbstractEditor<Operation> {
    @Inject
    private StockSummaryService stockSummaryService;

    @Named("fieldGroup.stock")
    private PickerField stockPicker;
    @Named("fieldGroup.type")
    private LookupField operationType;
    @Named("fieldGroup.amount")
    private TextField amount;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        operationType.addValueChangeListener(e -> {
            OperationType value = (OperationType) e.getValue();
            Stock stock = stockPicker.getValue();
            if (stock != null && value == Dividends) {
                StockSummary stockSummary = stockSummaryService.getStockSummary(stock.getId());
                amount.setValue(stockSummary.getAmount());
            }
        });
    }
}