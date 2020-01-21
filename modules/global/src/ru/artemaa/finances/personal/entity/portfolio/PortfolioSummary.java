package ru.artemaa.finances.personal.entity.portfolio;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import ru.artemaa.finances.personal.entity.Currency;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@MetaClass(name = "stocks$PortfolioSummary")
public class PortfolioSummary extends BaseUuidEntity {
    private static final long serialVersionUID = 6112335707367764180L;

    @MetaProperty
    protected Portfolio portfolio;

    @MetaProperty
    protected BigDecimal sum = valueOf(0.0);

    @MetaProperty
    protected Currency currency;

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getSum() {
        return sum;
    }


    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }


}