package mercy.digital.transfer.service.transaction;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.transaction.TransactionDao;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionType;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Inject
    private TransactionDao transactionDao;

    public void addEntityTransaction(TransactionEntity transactionEntity) {
        Dao<TransactionEntity, Integer> transactionDao = this.transactionDao.getTransactionDao();
        try {
            transactionDao.create(transactionEntity);
        } catch (SQLException e) {
            log.error("Cannot add Transaction entity " + e.getLocalizedMessage());
        }
    }

    public void setTransactionEntity(TransactionEntity transactionEntity,
                                     TransactionType transactionType,
                                     Double transactionAmount,
                                     CurrencyCode currencyCode,
                                     Integer clientAccountNo,
                                     Integer beneficiaryAccountNo) {

        transactionEntity.setSourceAccountNo(clientAccountNo);
        transactionEntity.setTargetAccountNo(beneficiaryAccountNo);
        transactionEntity.setAmount(transactionAmount);
        transactionEntity.setCurrency(currencyCode.name());
        transactionEntity.setTransactionType(transactionType.name());
        transactionEntity.setCreatedAt(Timestamp.from(Instant.now()));

        addEntityTransaction(transactionEntity);

    }

    public List<TransactionEntity> findEntitiesTransactionByAccountNo(Integer accountNo) {
        Dao<TransactionEntity, Integer> transactionDao = this.transactionDao.getTransactionDao();
        QueryBuilder<TransactionEntity, Integer> transactionQueryBuilder = transactionDao.queryBuilder();
        List<TransactionEntity> transactionEntities;
        try {
            PreparedQuery<TransactionEntity> accountQuery = transactionQueryBuilder.where().eq("SOURCE_ACCOUNT_NO", accountNo).prepare();
            transactionEntities = transactionDao.query(accountQuery);
        } catch (SQLException e) {
            log.error("Cannot find by account No " + accountNo + " Transaction entity " + e.getLocalizedMessage());
            return null;
        }
        return transactionEntities;
    }
    public TransactionEntity findEntityTransactionById(Integer id) {
        Dao<TransactionEntity, Integer> transactionDao = this.transactionDao.getTransactionDao();
        TransactionEntity transactionEntity;
        try {
            transactionEntity = transactionDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Transaction entity " + e.getLocalizedMessage());
            return null;
        }
        return transactionEntity;
    }

    public List<TransactionEntity> findAllTransactions() {
        Dao<TransactionEntity, Integer> transactionDao = this.transactionDao.getTransactionDao();
        List<TransactionEntity> transactionEntityList;
        try {
            transactionEntityList = transactionDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Transaction entity" + e.getLocalizedMessage());
            return null;
        }
        return transactionEntityList;
    }


}
