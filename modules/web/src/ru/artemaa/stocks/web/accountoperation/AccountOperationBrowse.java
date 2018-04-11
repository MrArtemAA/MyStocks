package ru.artemaa.stocks.web.accountoperation;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.EditAction;
import ru.artemaa.stocks.entity.operation.AccountOperation;
import ru.artemaa.stocks.entity.operation.AccountOperationType;
import ru.artemaa.stocks.web.action.CreateAccountOperationAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

import static ru.artemaa.stocks.entity.operation.AccountOperationType.*;

public class AccountOperationBrowse extends AbstractLookup {
    @Inject
    private Messages messages;

    @Inject
    private GroupTable<AccountOperation> accountOperationsTable;

    @Inject
    private Button createIncomeBtn;
    @Inject
    private Button createExpenseBtn;
    @Inject
    private Button createTransferBtn;
    @Named("accountOperationsTable.edit")
    private EditAction editAction;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        createIncomeBtn.setCaption(messages.getMessage(INCOME));
        createIncomeBtn.setAction(CreateAccountOperationAction.createIncomeAction(accountOperationsTable));

        createExpenseBtn.setCaption(messages.getMessage(EXPENSE));
        createExpenseBtn.setAction(CreateAccountOperationAction.createExpenseAction(accountOperationsTable));

        createTransferBtn.setCaption(messages.getMessage(TRANSFER));
        createTransferBtn.setAction(CreateAccountOperationAction.createTransferAction(accountOperationsTable));

        editAction.setBeforeActionPerformedHandler(() -> {
            AccountOperation operation = (AccountOperation) editAction.getTarget().getSingleSelected();
            AccountOperationType type = operation.getType();
            editAction.setWindowParams(ParamsMap.of("type", type));
            editAction.setWindowId(getOperationEditWindowId(type));
            return true;
        });
    }

    public static String getOperationEditWindowId(AccountOperationType type) {
        switch (type) {
            case INCOME:
            case EXPENSE:
                return "stocks$SimpleAccountOperation.edit";
            case TRANSFER:
                return "stocks$Transfer.edit";
        }
        return null;
    }
}