package ru.artemaa.stocks.web.transfer;

import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import ru.artemaa.stocks.entity.Account;
import ru.artemaa.stocks.entity.operation.Transfer;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class TransferEdit extends AbstractEditor<Transfer> {
    @Named("fieldGroup.sourceAccount")
    private LookupPickerField sourceAccountField;
    @Named("fieldGroup.destAccount")
    private LookupPickerField destAccountField;
    @Named("fieldGroup.sum")
    private TextField sum;
    @Named("fieldGroup.rate")
    private TextField rateField;
    @Inject
    private TextField destSum;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
    }

    @Override
    protected void postInit() {
        super.postInit();

        Transfer transfer = getItem();
        if (!PersistenceHelper.isNew(transfer)) {
            updateRateAndDestSumVisibility(transfer.getSourceAccount(), transfer.getDestAccount());
            updateDestSum(transfer.getSum(), transfer.getRate());
        }

        sourceAccountField.addValueChangeListener(
                e -> updateRateAndDestSumVisibility((Account) e.getValue(), transfer.getDestAccount()));

        destAccountField.addValueChangeListener(
                e -> updateRateAndDestSumVisibility((Account) e.getValue(), transfer.getSourceAccount()));

        sum.addValueChangeListener(e -> updateDestSum((BigDecimal) e.getValue(), transfer.getRate()));
        rateField.addValueChangeListener(e -> updateDestSum(transfer.getSum(), (BigDecimal) e.getValue()));

        /*destSum.addValueChangeListener(e -> {
            BigDecimal sum = getItem().getSum();
            if (sum != null) {
                rateField.setValue(((BigDecimal)destSum.getValue()).divide(sum));
            }
        });*/
    }

    private void updateDestSum(BigDecimal sum, BigDecimal rate) {
        if (sum != null && rate != null) {
            destSum.setValue(sum.multiply(rate));
        }
    }

    private void updateRateAndDestSumVisibility(Account account1, Account account2) {
        if (isAccountsSet(account1, account2)) {
            boolean differentCurrencies = isDifferentCurrencies(account1, account2);
            rateField.setVisible(differentCurrencies);
            destSum.setVisible(differentCurrencies);
            if (!differentCurrencies) {
                rateField.setValue(new BigDecimal(1));
            }
        }
    }

    private boolean isAccountsSet(Account account1, Account account2) {
        return Objects.nonNull(account1) && Objects.nonNull(account2);
    }

    private boolean isDifferentCurrencies(Account account1, Account account2) {
        return !account1.getCurrencyCode().equals(account2.getCurrencyCode());
    }
}