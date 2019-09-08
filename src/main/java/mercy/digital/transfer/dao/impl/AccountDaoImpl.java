package mercy.digital.transfer.dao.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.H2DatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.AccountDao;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.utils.H2DataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
public class AccountDaoImpl implements AccountDao {

    private static DataSourceConnectionSource connectionSource;

    static {
        try {
            DataSource hikariDS = H2DataSource.getDataSource();
            connectionSource = new DataSourceConnectionSource(hikariDS, new H2DatabaseType());
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public Dao<AccountEntity,Integer> getAccountDao () {
        Dao<AccountEntity,Integer> dao = null;
        try {
            dao = DaoManager.createDao(connectionSource, AccountEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Account DAO" + e.getLocalizedMessage());
        }
        return dao;
    }
}
