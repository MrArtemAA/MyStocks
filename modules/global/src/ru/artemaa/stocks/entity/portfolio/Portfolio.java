package ru.artemaa.stocks.entity.portfolio;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.OnDeleteInverse;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "STOCKS_PORTFOLIO")
@Entity(name = "stocks$Portfolio")
public class Portfolio extends StandardEntity {
    private static final long serialVersionUID = -9201527446197723777L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @OrderBy("date DESC")
    @Composition
    @OnDeleteInverse(DeletePolicy.UNLINK)
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "portfolio")
    protected List<PortfolioIncome> income;

    public void setIncome(List<PortfolioIncome> income) {
        this.income = income;
    }

    public List<PortfolioIncome> getIncome() {
        return income;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}