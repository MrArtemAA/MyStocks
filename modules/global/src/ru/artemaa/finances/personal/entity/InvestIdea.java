package ru.artemaa.finances.personal.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NamePattern("%s|name")
@Table(name = "STOCKS_INVEST_IDEA")
@Entity(name = "stocks$InvestIdea")
public class InvestIdea extends StandardEntity {
    private static final long serialVersionUID = 8765832010294909307L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Future
    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "END_DATE")
    protected Date endDate;

    @Lob
    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "ACTIVE")
    protected Boolean active = Boolean.TRUE;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
    }


}