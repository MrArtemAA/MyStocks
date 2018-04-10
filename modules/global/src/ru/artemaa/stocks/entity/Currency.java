package ru.artemaa.stocks.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseStringIdEntity;

@NamePattern("%s|name")
@MetaClass(name = "stocks$Currency")
public class Currency extends BaseStringIdEntity {
    private static final long serialVersionUID = -5687224572426293983L;

    @MetaProperty
    protected String id;

    @MetaProperty
    protected String name;

    @MetaProperty
    protected String code;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCode(String code) {
        this.code = code;
        id = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getId() {
        return code;
    }

    @Override
    public void setId(String id) {
        this.code = id;
    }
}