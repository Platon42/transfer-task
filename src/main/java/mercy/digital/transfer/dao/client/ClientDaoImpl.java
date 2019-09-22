package mercy.digital.transfer.dao.client;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.domain.ClientEntity;

import java.sql.SQLException;

@Slf4j
public class ClientDaoImpl implements ClientDao {

    @Inject
    private H2DataSourceService h2DataSourceService;

    public Dao<ClientEntity, Integer> getClientDao() {
        Dao<ClientEntity, Integer> dao = null;
        try {
            dao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), ClientEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Client DAO" + e.getLocalizedMessage());
        }
        return dao;
    }
}