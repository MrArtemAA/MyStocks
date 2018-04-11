package ru.artemaa.stocks.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DeletePolicy;
import ru.artemaa.stocks.service.CurrencyService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NamePattern("%s|name")
@Table(name = "STOCKS_ACCOUNT")
@Entity(name = "stocks$Account")
public class Account extends StandardEntity {
    private static final long serialVersionUID = -4845117845509203924L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_TYPE_ID")
    protected AccountType accountType;

    @NotNull
    @Column(name = "CURRENCY_CODE", nullable = false)
    protected String currencyCode;

    @Column(name = "AMOUNT")
    protected BigDecimal amount = new BigDecimal(0);

    @Lookup(type = LookupType.DROPDOWN)
    @Transient
    @MetaProperty
    protected Currency currency;

    @Column(name = "CONSIDER_IN_BALANCE")
    protected Boolean considerInBalance = true;

    @Lob
    @Column(name = "COMMENT_")
    protected String comment;

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public AccountType getAccountType() {
        return accountType;
    }


    public void setConsiderInBalance(Boolean considerInBalance) {
        this.considerInBalance = considerInBalance;
    }

    public Boolean getConsiderInBalance() {
        return considerInBalance;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }


    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        currencyCode = currency.getCode();
    }

    public Currency getCurrency() {
        if (currency == null && currencyCode != null) {
            currency = AppBeans.get(CurrencyService.class).get(currencyCode);
        }
        return currency;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}