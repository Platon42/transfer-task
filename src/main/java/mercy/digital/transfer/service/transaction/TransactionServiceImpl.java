package mercy.digital.transfer.service.transaction;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.transaction.TransactionDao;
import mercy.digital.transfer.domain.TransactionEntity;

import java.sql.SQLException;
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
