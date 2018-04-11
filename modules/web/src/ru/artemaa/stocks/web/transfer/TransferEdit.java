package ru.artemaa.stocks.web.transfer;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import ru.artemaa.stocks.entity.Account;
import ru.artemaa.stocks.entity.operation.Transfer;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Objects;

public class TransferEdit extends AbstractEditor<Transfer> {
    @Named("fieldGroup.sourceAccount")
    private LookupPickerField sourceAccountField;
    @Named("fieldGroup.destAccount")
    private LookupPickerField destAccountField;
    @Named("fieldGroup.rate")
    private TextField rateField;
    @Inject
    private TextField destSum;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        rateField.setVisible(false);
        destSum.setVisible(false);
    }

    @Override
    protected void postInit() {
        super.postInit();

        sourceAccountField.addValueChangeListener(e -> {
            Account account = (Account) e.getValue();
            Account destAccount = getItem().getDestAccount();
            if (Objects.nonNull(account) && Objects.nonNull(destAccount)) {
                if (isDifferentCurrencies(account, destAccount)) {
                    rateField.setVisible(true);
                    destSum.setVisible(true);
                } else {
                    rateField.setVisible(false);
                    destSum.setVisible(false);
                }
            }
        });
    }

    private boolean isDifferentCurrencies(Account account1, Account account2) {
        return !account1.getCurrencyCode().equals(account2.getCurrencyCode());
    }
}