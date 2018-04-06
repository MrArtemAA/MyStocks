package ru.artemaa.stocks.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.*;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DeletePolicy;
import org.hibernate.validator.constraints.Length;
import ru.artemaa.stocks.service.CurrencyService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamePattern("%s (%s)|name,code")
@Table(name = "STOCKS_STOCK")
@Entity(name = "stocks$Stock")
public class Stock extends StandardEntity {
    private static final long serialVersionUID = 47389431230851613L;

    @Length(min = 3, max = 80)
    @NotNull
    @Column(name = "NAME", nullable = false, unique = true, length = 80)
    protected String name;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup"})
    @NotNull
    @OnDeleteInverse(DeletePolicy.DENY)
    @OnDelete(DeletePolicy.UNLINK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INDUSTRY_ID")
    protected Industry industry;

    @Length(min = 1, max = 20)
    @NotNull
    @Column(name = "CODE", nullable = false, unique = true, length = 20)
    protected String code;

    @Transient
    @MetaProperty
    protected Currency currency;

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public Industry getIndustry() {
        return industry;
    }

    @CaseConversion
    @NotNull
    @Column(name = "CURRENCY_CODE")
    protected String currencyCode;

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
        setCurrencyCode(currency.getCode());
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}