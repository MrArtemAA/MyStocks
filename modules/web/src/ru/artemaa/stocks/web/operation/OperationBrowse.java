package ru.artemaa.stocks.web.operation;

import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.actions.EditAction;

import javax.inject.Named;
import java.util.Map;

import static com.haulmont.cuba.gui.WindowManager.OpenType.DIALOG;

public class OperationBrowse extends AbstractLookup {
    @Named("operationsTable.edit")
    EditAction editAction;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        editAction.getBulkEditorIntegration()
                .setEnabled(true)
                .setOpenType(DIALOG);
    }
}