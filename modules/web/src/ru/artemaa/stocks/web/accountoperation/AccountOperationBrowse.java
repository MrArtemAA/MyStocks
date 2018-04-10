package ru.artemaa.stocks.web.accountoperation;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import ru.artemaa.stocks.entity.operation.AccountOperation;
import ru.artemaa.stocks.web.action.CreateIncomeAction;

import javax.inject.Inject;
import java.util.Map;

public class AccountOperationBrowse extends AbstractLookup {
    @Inject
    private GroupTable<AccountOperation> accountOperationsTable;
    @Inject
    private Button createIncomeBtn;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        createIncomeBtn.setAction(new CreateIncomeAction(accountOperationsTable));
    }
}