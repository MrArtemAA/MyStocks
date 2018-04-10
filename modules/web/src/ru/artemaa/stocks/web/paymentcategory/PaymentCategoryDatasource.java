package ru.artemaa.stocks.web.paymentcategory;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import ru.artemaa.stocks.entity.PaymentCategory;
import ru.artemaa.stocks.entity.operation.AccountOperationType;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class PaymentCategoryDatasource extends CustomCollectionDatasource<PaymentCategory, UUID> {
    @Inject
    private DataManager dataManager;

    @Override
    protected Collection<PaymentCategory> getEntities(Map<String, Object> params) {
        AccountOperationType type = (AccountOperationType) Objects.requireNonNull(params.get("type"));
        return dataManager.loadList(LoadContext
                .create(PaymentCategory.class)
                .setQuery(LoadContext
                        .createQuery("select e from stocks$PaymentCategory e where e.type = :type")
                        .setParameter("type", type)));
    }
}
