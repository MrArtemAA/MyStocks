package ru.artemaa.stocks.entity.portfolio;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "STOCKS_PORTFOLIO")
@Entity(name = "stocks$Portfolio")
public class Portfolio extends StandardEntity {
    private static final long serialVersionUID = -9201527446197723777L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}