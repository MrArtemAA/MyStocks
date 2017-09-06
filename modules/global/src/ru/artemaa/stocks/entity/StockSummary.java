package ru.artemaa.stocks.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.AbstractNotPersistentEntity;

@MetaClass(name = "stocks$StockSummary")
public class StockSummary extends AbstractNotPersistentEntity {
    private static final long serialVersionUID = 5580946325692918983L;

    @MetaProperty(mandatory = true)
    protected Stock stock;

    @MetaProperty
    protected Integer amount = 0;

    @MetaProperty
    protected Double totalPurchasePrice = 0.0;

    @MetaProperty
    protected Double avgPurchasePrice = 0.0;

    @MetaProperty
    protected Double totalSellPrice = 0.0;

    @MetaProperty
    protected Double avgSellPrice = 0.0;

    @MetaProperty
    protected Double totalDividends = 0.0;

    @MetaProperty
    protected Double avgDividends = 0.0;

    @MetaProperty
    protected Double priceDividendsRatio = 0.0;

    public void setAvgPurchasePrice(Double avgPurchasePrice) {
        this.avgPurchasePrice = avgPurchasePrice;
    }

    public Double getAvgPurchasePrice() {
        return avgPurchasePrice;
    }

    public void setTotalSellPrice(Double totalSellPrice) {
        this.totalSellPrice = totalSellPrice;
    }

    public Double getTotalSellPrice() {
        return totalSellPrice;
    }

    public void setAvgSellPrice(Double avgSellPrice) {
        this.avgSellPrice = avgSellPrice;
    }

    public Double getAvgSellPrice() {
        return avgSellPrice;
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

    public void setAvgDividends(Double avgDividends) {
        this.avgDividends = avgDividends;
    }

    public Double getAvgDividends() {
        return avgDividends;
    }

    public void setPriceDividendsRatio(Double priceDividendsRatio) {
        this.priceDividendsRatio = priceDividendsRatio;
    }

    public Double getPriceDividendsRatio() {
        return priceDividendsRatio;
    }


}