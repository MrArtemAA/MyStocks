package ru.artemaa.stocks.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import ru.artemaa.stocks.entity.portfolio.Portfolio;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;

@MetaClass(name = "stocks$StockSummary")
public class StockSummary extends BaseUuidEntity {
    private static final long serialVersionUID = 5580946325692918983L;

    @MetaProperty
    protected Portfolio portfolio;

    @MetaProperty(mandatory = true)
    protected Stock stock;

    @MetaProperty
    protected Integer amount = 0;

    @MetaProperty
    protected Double totalPurchasePrice = 0.0;

    @MetaProperty
    protected BigDecimal avgPurchasePrice = valueOf(0.0);

    @MetaProperty
    protected Double totalSellPrice = 0.0;

    @MetaProperty
    protected BigDecimal avgSellPrice = valueOf(0.0);

    @MetaProperty
    protected Double totalDividends = 0.0;

    @MetaProperty
    protected BigDecimal avgDividends = valueOf(0.0);

    @MetaProperty
    protected BigDecimal priceDividendsRatio = valueOf(0.0);

    public BigDecimal getPriceDividendsRatio() {
        return priceDividendsRatio;
    }

    public void setPriceDividendsRatio(BigDecimal priceDividendsRatio) {
        this.priceDividendsRatio = priceDividendsRatio;
    }


    public BigDecimal getAvgPurchasePrice() {
        return avgPurchasePrice;
    }

    public void setAvgPurchasePrice(BigDecimal avgPurchasePrice) {
        this.avgPurchasePrice = avgPurchasePrice;
    }


    public BigDecimal getAvgSellPrice() {
        return avgSellPrice;
    }

    public void setAvgSellPrice(BigDecimal avgSellPrice) {
        this.avgSellPrice = avgSellPrice;
    }


    public BigDecimal getAvgDividends() {
        return avgDividends;
    }

    public void setAvgDividends(BigDecimal avgDividends) {
        this.avgDividends = avgDividends;
    }


    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }


    public void setTotalSellPrice(Double totalSellPrice) {
        this.totalSellPrice = totalSellPrice;
    }

    public Double getTotalSellPrice() {
        return totalSellPrice;
    }


    public void setTotalPurchasePrice(Double totalPurchasePrice) {
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public Double getTotalPurchasePrice() {
        return totalPurchasePrice;
    }


    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock() {
        return stock;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setTotalDividends(Double totalDividends) {
        this.totalDividends = totalDividends;
    }

    public Double getTotalDividends() {
        return totalDividends;
    }


}