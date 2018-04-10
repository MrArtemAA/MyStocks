package ru.artemaa.stocks.web.action;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.actions.CreateAction;

public class CreateIncomeAction extends CreateAction {

    public CreateIncomeAction(ListComponent target) {
        super(target);
    }

    public CreateIncomeAction(ListComponent target, WindowManager.OpenType openType) {
        super(target, openType);
    }

    public CreateIncomeAction(ListComponent target, WindowManager.OpenType openType, String id) {
        super(target, openType, id);
    }

    @Override
    public String getWindowId() {
        return "stocks$SimpleAccountOperation.edit";
    }
}
