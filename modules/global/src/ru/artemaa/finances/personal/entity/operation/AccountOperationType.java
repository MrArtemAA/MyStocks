package ru.artemaa.finances.personal.entity.operation;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum AccountOperationType implements EnumClass<String> {

    INCOME("INCOME"),
    EXPENSE("EXPENSE"),
    TRANSFER("TRANSFER");

    private String id;

    AccountOperationType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static AccountOperationType fromId(String id) {
        for (AccountOperationType at : AccountOperationType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}