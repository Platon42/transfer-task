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

    private final H2DataSourceService h2DataSourceService;
    private Dao<ClientEntity, Integer> dao = null;

    @Inject
    public ClientDaoImpl(H2DataSourceService h2DataSourceService) {
        this.h2DataSourceService = h2DataSourceService;
    }

    public Dao<ClientEntity, Integer> getClientDao() {
        try {
            dao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), ClientEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Client DAO" + e.getLocalizedMessage());
        }
        return dao;
    }

}