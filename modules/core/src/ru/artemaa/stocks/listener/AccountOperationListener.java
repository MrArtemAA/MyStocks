package ru.artemaa.stocks.listener;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.listener.BeforeDeleteEntityListener;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import org.springframework.stereotype.Component;
import ru.artemaa.stocks.entity.Account;
import ru.artemaa.stocks.entity.operation.AccountOperation;
import ru.artemaa.stocks.entity.operation.SimpleAccountOperation;
import ru.artemaa.stocks.entity.operation.Transfer;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.UUID;

@Component("stocks_AccountOperationListener")
public class AccountOperationListener implements
        BeforeDeleteEntityListener<AccountOperation>,
        BeforeInsertEntityListener<AccountOperation>,
        BeforeUpdateEntityListener<AccountOperation> {

    @Inject
    private Persistence persistence;

    @Override
    public void onBeforeDelete(AccountOperation entity, EntityManager entityManager) {
        switch (entity.getType()) {
            case INCOME:
            case EXPENSE:
                deleteSimpleOperation((SimpleAccountOperation) entity, entityManager);
                break;
            case TRANSFER:
                deleteTransfer((Transfer) entity, entityManager);
        }
    }

    private void deleteSimpleOperation(SimpleAccountOperation operation, EntityManager entityManager) {
        Account account = entityManager.find(Account.class, operation.getAccount().getId());
        BigDecimal accountAmount = account.getAmount();
        switch (operation.getType()) {
            case INCOME:
                accountAmount = accountAmount.subtract(operation.getSum());
                break;
            case EXPENSE:
                accountAmount = accountAmount.add(operation.getSum());
                break;
        }
        account.setAmount(accountAmount);
        entityManager.persist(account);
    }

    private void deleteTransfer(Transfer transfer, EntityManager entityManager) {
        Account sourceAccount = entityManager.find(Account.class, transfer.getSourceAccount().getId());
        Account destAccount = entityManager.find(Account.class, transfer.getDestAccount().getId());
        /*BigDecimal sourceAccountAmount = sourceAccount.getAmount();
        BigDecimal destAccountAmount = destAccount.getAmount();*/
        sourceAccount.setAmount(sourceAccount.getAmount().add(transfer.getSum()));
        destAccount.setAmount(destAccount.getAmount().subtract(transfer.getSum()));
        entityManager.persist(sourceAccount);
        entityManager.persist(destAccount);
    }

    @Override
    public void onBeforeInsert(AccountOperation entity, EntityManager entityManager) {
        switch (entity.getType()) {
            case INCOME:
            case EXPENSE:
                insertSimpleOperation((SimpleAccountOperation) entity, entityManager);
                break;
            case TRANSFER:
                insertTransfer((Transfer) entity, entityManager);
        }
    }

    private void insertSimpleOperation(SimpleAccountOperation operation, EntityManager entityManager) {
        Account account = entityManager.find(Account.class, operation.getAccount().getId());
        BigDecimal accountAmount = account.getAmount();
        switch (operation.getType()) {
            case INCOME:
                accountAmount = accountAmount.add(operation.getSum());
                break;
            case EXPENSE:
                accountAmount = accountAmount.subtract(operation.getSum());
                break;
        }
        account.setAmount(accountAmount);
        entityManager.persist(account);
    }

    private void insertTransfer(Transfer transfer, EntityManager entityManager) {
        Account sourceAccount = entityManager.find(Account.class, transfer.getSourceAccount().getId());
        Account destAccount = entityManager.find(Account.class, transfer.getDestAccount().getId());
        /*BigDecimal sourceAccountAmount = sourceAccount.getAmount();
        BigDecimal destAccountAmount = destAccount.getAmount();*/
        sourceAccount.setAmount(sourceAccount.getAmount().subtract(transfer.getSum()));
        destAccount.setAmount(destAccount.getAmount().add(transfer.getSum()));
        entityManager.persist(sourceAccount);
        entityManager.persist(destAccount);
    }

    @Override
    public void onBeforeUpdate(AccountOperation entity, EntityManager entityManager) {
        switch (entity.getType()) {
            case INCOME:
            case EXPENSE:
                updateSimpleOperation((SimpleAccountOperation) entity, entityManager);
                break;
            case TRANSFER:
                updateTransfer((Transfer) entity, entityManager);
        }
    }

    private void updateSimpleOperation(SimpleAccountOperation operation, EntityManager entityManager) {
        Account account = entityManager.find(Account.class, operation.getAccount().getId());
        BigDecimal accountAmount = account.getAmount();
        AccountOperation oldOperation = getOldOperation(operation.getId());
        switch (operation.getType()) {
            case INCOME:
                accountAmount = accountAmount.subtract(oldOperation.getSum()).add(operation.getSum());
                break;
            case EXPENSE:
                accountAmount = accountAmount.add(oldOperation.getSum()).subtract(operation.getSum());
                break;
        }
        account.setAmount(accountAmount);
        entityManager.persist(account);
    }

    private void updateTransfer(Transfer transfer, EntityManager entityManager) {
        Account sourceAccount = entityManager.find(Account.class, transfer.getSourceAccount().getId());
        Account destAccount = entityManager.find(Account.class, transfer.getDestAccount().getId());
        AccountOperation oldOperation = getOldOperation(transfer.getId());
        /*BigDecimal sourceAccountAmount = sourceAccount.getAmount();
        BigDecimal destAccountAmount = destAccount.getAmount();*/
        sourceAccount.setAmount(sourceAccount.getAmount().add(oldOperation.getSum()).subtract(transfer.getSum()));
        destAccount.setAmount(destAccount.getAmount().subtract(oldOperation.getSum()).add(transfer.getSum()));
        entityManager.persist(sourceAccount);
        entityManager.persist(destAccount);
    }

    private AccountOperation getOldOperation(UUID id) {
        AccountOperation operation;
        try (Transaction transaction = persistence.createTransaction()) {
            EntityManager entityManager = persistence.getEntityManager();
            operation = entityManager.find(AccountOperation.class, id);
            transaction.commit();
        }
        return operation;
    }

}