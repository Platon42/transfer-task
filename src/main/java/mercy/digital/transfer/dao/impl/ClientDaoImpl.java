package mercy.digital.transfer.dao.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.H2DatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.ClientDao;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.utils.H2DataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Slf4j
public class ClientDaoImpl implements ClientDao {

    private static DataSourceConnectionSource connectionSource;

    static {
        try {
            DataSource hikariDS = H2DataSource.getDataSource();
            connectionSource = new DataSourceConnectionSource(hikariDS, new H2DatabaseType());
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public Dao<ClientEntity, Integer> getClientDao() {
        Dao<ClientEntity, Integer> dao = null;
        try {
            dao = DaoManager.createDao(connectionSource, ClientEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Client DAO" + e.getLocalizedMessage());
        }
        return dao;
    }
}