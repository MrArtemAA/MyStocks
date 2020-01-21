package ru.artemaa.finances.personal.web.accountoperation;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.EditAction;
import ru.artemaa.finances.personal.entity.Account;
import ru.artemaa.finances.personal.entity.operation.AccountOperation;
import ru.artemaa.finances.personal.entity.operation.AccountOperationType;
import ru.artemaa.finances.personal.entity.operation.SimpleAccountOperation;
import ru.artemaa.finances.personal.entity.operation.Transfer;
import ru.artemaa.finances.personal.web.action.CreateAccountOperationAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.Map;

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

        createIncomeBtn.setCaption(messages.getMessage(AccountOperationType.INCOME));
        createIncomeBtn.setAction(CreateAccountOperationAction.createIncomeAction(accountOperationsTable));

        createExpenseBtn.setCaption(messages.getMessage(AccountOperationType.EXPENSE));
        createExpenseBtn.setAction(CreateAccountOperationAction.createExpenseAction(accountOperationsTable));

        createTransferBtn.setCaption(messages.getMessage(AccountOperationType.TRANSFER));
        createTransferBtn.setAction(CreateAccountOperationAction.createTransferAction(accountOperationsTable));

        editAction.setBeforeActionPerformedHandler(() -> {
            AccountOperation operation = (AccountOperation) editAction.getTarget().getSingleSelected();
            AccountOperationType type = operation.getType();
            editAction.setWindowParams(ParamsMap.of("type", type));
            editAction.setWindowId(getOperationEditWindowId(type));
            return true;
        });

        accountOperationsTable.setIconProvider(entity -> {
            switch (entity.getType()) {
                case INCOME: return "font-icon:PLUS";//"icons/plus-btn.png";
                case EXPENSE: return "font-icon:MINUS";//"icons/minus.png";
                case TRANSFER: return "font-icon:ARROW_RIGHT";//"icons/arrow.png";
                default: return "";
            }
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

    public Component generateAccountColumn(AccountOperation operation) {
        switch (operation.getType()) {
            case INCOME:
            case EXPENSE:
                SimpleAccountOperation simpleAccountOperation = (SimpleAccountOperation) operation;
                return new Table.PlainTextCell(simpleAccountOperation.getAccount().getInstanceName() +
                        " (" + simpleAccountOperation.getCategory().getName() + ")");
            case TRANSFER:
                Transfer transfer = (Transfer) operation;
                Account sourceAccount = transfer.getSourceAccount();
                Account destAccount = transfer.getDestAccount();
                return new Table.PlainTextCell(sourceAccount.getInstanceName() + " на " + destAccount.getInstanceName());
        }
        return null;
    }

    public Component generateSumColumn(AccountOperation operation) {
        switch (operation.getType()) {
            case INCOME:
            case EXPENSE:
                SimpleAccountOperation simpleAccountOperation = (SimpleAccountOperation) operation;
                String currencyCode = simpleAccountOperation.getAccount().getCurrencyCode();
                return new Table.PlainTextCell(simpleAccountOperation.getSum() + " " + currencyCode);
            case TRANSFER:
                Transfer transfer = (Transfer) operation;
                String sourceCurrencyCode = transfer.getSourceAccount().getCurrencyCode();
                String destCurrencyCode = transfer.getDestAccount().getCurrencyCode();
                BigDecimal sum = transfer.getSum();
                BigDecimal convertedSum = sum.multiply(transfer.getRate());
                if (sourceCurrencyCode.equals(destCurrencyCode)) {
                    return new Table.PlainTextCell(sum + " " + sourceCurrencyCode);
                } else {
                    return new Table.PlainTextCell(sum + " " + sourceCurrencyCode +
                            " (" + convertedSum + " " + destCurrencyCode + ")");
                }
        }
        return null;
    }
}