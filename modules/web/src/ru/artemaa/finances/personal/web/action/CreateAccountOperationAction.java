package ru.artemaa.finances.personal.web.action;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import ru.artemaa.finances.personal.entity.operation.AccountOperationType;
import ru.artemaa.finances.personal.web.accountoperation.AccountOperationBrowse;

import java.util.Map;

public class CreateAccountOperationAction extends CreateAction {

    private AccountOperationType type;

    public static CreateAccountOperationAction createIncomeAction(ListComponent target) {
        return new CreateAccountOperationAction(target, AccountOperationType.INCOME);
    }

    public static CreateAccountOperationAction createExpenseAction(ListComponent target) {
        return new CreateAccountOperationAction(target, AccountOperationType.EXPENSE);
    }

    public static CreateAccountOperationAction createTransferAction(ListComponent target) {
        return new CreateAccountOperationAction(target, AccountOperationType.TRANSFER);
    }

    private CreateAccountOperationAction(ListComponent target, AccountOperationType type) {
        super(target);
        this.type = type;
        Map<String, Object> paramsMap = ParamsMap.of("type", type);
        setWindowParams(paramsMap);
        setInitialValues(paramsMap);
        setWindowId(AccountOperationBrowse.getOperationEditWindowId(type));
        setOpenType(WindowManager.OpenType.DIALOG);
    }
}
