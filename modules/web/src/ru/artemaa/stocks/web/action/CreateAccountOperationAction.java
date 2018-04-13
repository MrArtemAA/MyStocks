package ru.artemaa.stocks.web.action;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import ru.artemaa.stocks.entity.operation.AccountOperationType;

import java.util.Map;

import static ru.artemaa.stocks.entity.operation.AccountOperationType.*;
import static ru.artemaa.stocks.web.accountoperation.AccountOperationBrowse.getOperationEditWindowId;

public class CreateAccountOperationAction extends CreateAction {

    private AccountOperationType type;

    public static CreateAccountOperationAction createIncomeAction(ListComponent target) {
        return new CreateAccountOperationAction(target, INCOME);
    }

    public static CreateAccountOperationAction createExpenseAction(ListComponent target) {
        return new CreateAccountOperationAction(target, EXPENSE);
    }

    public static CreateAccountOperationAction createTransferAction(ListComponent target) {
        return new CreateAccountOperationAction(target, TRANSFER);
    }

    private CreateAccountOperationAction(ListComponent target, AccountOperationType type) {
        super(target);
        this.type = type;
        Map<String, Object> paramsMap = ParamsMap.of("type", type);
        setWindowParams(paramsMap);
        setInitialValues(paramsMap);
        setWindowId(getOperationEditWindowId(type));
        setOpenType(WindowManager.OpenType.DIALOG);
    }
}
