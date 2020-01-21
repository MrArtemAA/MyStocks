package ru.artemaa.finances.personal.web.portfolio;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.artemaa.finances.personal.entity.portfolio.Portfolio;
import ru.artemaa.finances.personal.entity.portfolio.PortfolioSummary;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

import static com.haulmont.cuba.core.global.PersistenceHelper.isNew;

public class PortfolioEdit extends AbstractEditor<Portfolio> {
    @Inject
    private TextField incomeAmount;
    @Named("portfolioSummariesDs")
    private CollectionDatasource<PortfolioSummary, UUID> portfolioSummaryDs;

    @Override
    protected void postInit() {
        super.postInit();
        if (!isNew(getItem())) {
            updateSummary();
        }
    }

    private void updateSummary() {
        portfolioSummaryDs.refresh(ParamsMap.of("portfolio", getItem()));
    }
}