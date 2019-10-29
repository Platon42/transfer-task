package mercy.digital.transfer.dao.client.account;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.domain.ClientAccountEntity;

import java.sql.SQLException;

@Slf4j
public class ClientAccountDaoImpl implements ClientAccountDao {

    private final H2DataSourceService h2DataSourceService;

    @Inject
    public ClientAccountDaoImpl(H2DataSourceService h2DataSourceService) {
        this.h2DataSourceService = h2DataSourceService;
    }

    public Dao<ClientAccountEntity,Integer> getAccountDao () {
        Dao<ClientAccountEntity,Integer> dao = null;
        try {
            dao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), ClientAccountEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Client Account DAO" + e.getLocalizedMessage());
        }
        return dao;
    }
}
