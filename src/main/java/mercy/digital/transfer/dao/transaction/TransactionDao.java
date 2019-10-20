package mercy.digital.transfer.dao.transaction;

import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.TransactionEntity;

public interface TransactionDao {
    Dao<TransactionEntity, Integer> getTransactionDao();
}
