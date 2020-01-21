package ru.artemaa.finances.personal.web.accountoperation;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.data.impl.CustomGroupDatasource;
import ru.artemaa.finances.personal.entity.operation.AccountOperation;
import ru.artemaa.finances.personal.entity.operation.SimpleAccountOperation;
import ru.artemaa.finances.personal.entity.operation.Transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Artem Areshko
 * 13.04.2018
 */
public class AccountOperationsDatasource extends CustomGroupDatasource<AccountOperation, UUID> {
    private static final String SIMPLE_OPERATIONS_QUERY = "select o from stocks$SimpleAccountOperation o";
    private static final String TRANSFERS_QUERY = "select o from stocks$Transfer o";

    @Override
    protected Collection<AccountOperation> getEntities(Map<String, Object> params) {
        DataManager dataManager = AppBeans.get(DataManager.class);
        Collection<AccountOperation> result = new ArrayList<>();
        result.addAll(dataManager.loadList(LoadContext.create(SimpleAccountOperation.class)
                .setQuery(LoadContext.createQuery(SIMPLE_OPERATIONS_QUERY))
                .setView("simpleAccountOperation-detailed-view")));

        result.addAll(dataManager.loadList(LoadContext.create(Transfer.class)
                .setQuery(LoadContext.createQuery(TRANSFERS_QUERY))
                .setView("transfer-detailed-view")));

        return result.stream()
                .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                .collect(Collectors.toList());
    }
}
