package ru.artemaa.stocks.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "STOCKS_OPERATION")
@Entity(name = "stocks$Operation")
public class Operation extends StandardEntity {
    private static final long serialVersionUID = 7569389514187153852L;

    @NotNull
    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_ID")
    protected Stock stock;

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected Integer type;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "DATE_", nullable = false)
    protected Date date = new Date();

    @DecimalMin("0")
    @NotNull
    @Column(name = "PRICE", nullable = false)
    protected Double price;

    @Min(1)
    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    protected Integer amount;


    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setType(OperationType type) {
        this.type = type == null ? null : type.getId();
    }

    public OperationType getType() {
        return type == null ? null : OperationType.fromId(type);
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }


}