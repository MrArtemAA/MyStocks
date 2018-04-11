package ru.artemaa.stocks.web.transfer;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.components.TextField;
import ru.artemaa.stocks.entity.operation.Transfer;

import javax.inject.Named;
import java.util.Map;

public class TransferEdit extends AbstractEditor<Transfer> {
    @Named("fieldGroup.sourceAccount")
    private LookupPickerField sourceAccountField;
    @Named("fieldGroup.destAccount")
    private LookupPickerField destAccountField;
    @Named("fieldGroup.rate")
    private TextField rateField;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        rateField.setVisible(false);
    }

    @Override
    protected void postInit() {
        super.postInit();

        /*sourceAccountField.addValueChangeListener(e -> {
            Account account = (Account) e.getValue();
            if (Objects.nonNull(account) && Objects.nonNull(getItem().getDestAccount())) {

            }
        });*/
    }
}