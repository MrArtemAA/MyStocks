package ru.artemaa.stocks.web.simpleaccountoperation;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import ru.artemaa.stocks.entity.PaymentCategory;
import ru.artemaa.stocks.entity.operation.AccountOperationType;
import ru.artemaa.stocks.entity.operation.SimpleAccountOperation;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class SimpleAccountOperationEdit extends AbstractEditor<SimpleAccountOperation> {
    @Inject
    private CollectionDatasource<PaymentCategory, UUID> categoriesDs;
    @Inject
    private Messages messages;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        AccountOperationType type = (AccountOperationType) params.get("type");
        setCaption(messages.getMessage(type));
        categoriesDs.refresh(ParamsMap.of("type", type));
    }
}