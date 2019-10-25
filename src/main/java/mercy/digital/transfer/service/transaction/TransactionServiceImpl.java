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
import java.util.Collections;
import java.util.List;

@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Inject
    private TransactionDao dao;

    public Integer addEntityTransaction(TransactionEntity transactionEntity) {
        Dao<TransactionEntity, Integer> daoTransactionDao = this.dao.getTransactionDao();
        try {
            daoTransactionDao.create(transactionEntity);
            return transactionEntity.getTransactionId();
        } catch (SQLException e) {
            log.error("Cannot add Transaction entity " + e.getLocalizedMessage());
            return null;
        }
    }

    public Integer setTransactionEntity(TransactionEntity transactionEntity,
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

        return transactionEntity.getTransactionId();

    }

    public List<TransactionEntity> findEntitiesTransactionByAccountNo(Integer accountNo) {
        Dao<TransactionEntity, Integer> daoTransactionDao = this.dao.getTransactionDao();
        QueryBuilder<TransactionEntity, Integer> transactionQueryBuilder = daoTransactionDao.queryBuilder();
        List<TransactionEntity> transactionEntities;
        try {
            PreparedQuery<TransactionEntity> accountQuery = transactionQueryBuilder.where().eq("SOURCE_ACCOUNT_NO", accountNo).prepare();
            transactionEntities = daoTransactionDao.query(accountQuery);
        } catch (SQLException e) {
            log.error("Cannot find by account No " + accountNo + " Transaction entity " + e.getLocalizedMessage());
            return Collections.emptyList();
        }
        return transactionEntities;
    }
    public TransactionEntity findEntityTransactionById(Integer id) {
        Dao<TransactionEntity, Integer> daoTransactionDao = this.dao.getTransactionDao();
        TransactionEntity transactionEntity;
        try {
            transactionEntity = daoTransactionDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Transaction entity " + e.getLocalizedMessage());
            return null;
        }
        return transactionEntity;
    }

    public List<TransactionEntity> findAllTransactions() {
        Dao<TransactionEntity, Integer> daoTransactionDao = this.dao.getTransactionDao();
        List<TransactionEntity> transactionEntityList;
        try {
            transactionEntityList = daoTransactionDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Transaction entity" + e.getLocalizedMessage());
            return Collections.emptyList();
        }
        return transactionEntityList;
    }


}
