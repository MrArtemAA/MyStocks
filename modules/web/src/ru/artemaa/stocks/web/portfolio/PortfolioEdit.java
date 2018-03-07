package ru.artemaa.stocks.web.portfolio;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.EditAction;
import ru.artemaa.stocks.entity.portfolio.Portfolio;
import ru.artemaa.stocks.entity.portfolio.PortfolioIncome;

import javax.inject.Inject;
import javax.inject.Named;

public class PortfolioEdit extends AbstractEditor<Portfolio> {
    @Inject
    private TextField incomeAmount;
    @Named("incomeTable.create")
    private CreateAction createAction;
    @Named("incomeTable.edit")
    private EditAction editAction;

    @Override
    protected void postInit() {
        super.postInit();
        updateTotalAmount();

        createAction.setAfterCommitHandler(entity -> updateTotalAmount());
        editAction.setAfterCommitHandler(entity -> updateTotalAmount());
    }

    private void updateTotalAmount() {
        long sum = getItem().getIncome().stream().mapToLong(PortfolioIncome::getAmount).sum();
        incomeAmount.setValue(sum);
    }
}