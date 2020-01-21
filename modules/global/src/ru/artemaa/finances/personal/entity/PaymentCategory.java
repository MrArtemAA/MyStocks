package ru.artemaa.finances.personal.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import ru.artemaa.finances.personal.entity.operation.AccountOperationType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "STOCKS_PAYMENT_CATEGORY")
@Entity(name = "stocks$PaymentCategory")
public class PaymentCategory extends StandardEntity {
    private static final long serialVersionUID = -5022818445252373335L;

    @Column(name = "TYPE_")
    protected String type;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "ACTIVE")
    protected Boolean active = true;

    public AccountOperationType getType() {
        return type == null ? null : AccountOperationType.fromId(type);
    }

    public void setType(AccountOperationType type) {
        this.type = type == null ? null : type.getId();
    }


    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}