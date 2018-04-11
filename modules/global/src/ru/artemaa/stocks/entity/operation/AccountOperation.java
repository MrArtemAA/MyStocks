package ru.artemaa.stocks.entity.operation;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Listeners("stocks_AccountOperationListener")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE", discriminatorType = DiscriminatorType.STRING)
@Table(name = "STOCKS_ACCOUNT_OPERATION")
@Entity(name = "stocks$AccountOperation")
public class AccountOperation extends StandardEntity {
    private static final long serialVersionUID = 8067949292710561489L;

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    protected String type;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "DATE_", nullable = false)
    protected Date date = new Date();

    @DecimalMin("0")
    @NotNull
    @Column(name = "SUM_")
    protected BigDecimal sum;

    @Lob
    @Column(name = "COMMENT_")
    protected String comment;

    public void setType(AccountOperationType type) {
        this.type = type == null ? null : type.getId();
    }

    public AccountOperationType getType() {
        return type == null ? null : AccountOperationType.fromId(type);
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }


    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }


}