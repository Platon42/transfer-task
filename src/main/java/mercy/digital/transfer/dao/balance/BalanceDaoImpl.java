package mercy.digital.transfer.dao.balance;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.domain.BalanceEntity;

import java.sql.SQLException;

@Slf4j
public class BalanceDaoImpl implements BalanceDao {

    @Inject
    private H2DataSourceService h2DataSourceService;

    public Dao<BalanceEntity, Integer> getBalanceDao() {
        Dao<BalanceEntity, Integer> dao;
        try {
            dao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), BalanceEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Balance DAO" + e.getLocalizedMessage());
            return null;
        }
        return dao;
    }
}
