package mercy.digital.transfer.dao.transaction;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.domain.TransactionEntity;

import java.sql.SQLException;

@Slf4j
public class TransactionDaoImpl implements TransactionDao {

    @Inject
    private H2DataSourceService h2DataSourceService;

    public Dao<TransactionEntity, Integer> getTransactionDao() {
        Dao<TransactionEntity, Integer> dao = null;
        try {
            dao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), TransactionEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Transaction DAO" + e.getLocalizedMessage());
        }
        return dao;
    }
}
