package mercy.digital.transfer.service.client;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.client.ClientDao;
import mercy.digital.transfer.domain.ClientEntity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {

    private final ClientDao dao;

    @Inject
    public ClientServiceImpl(ClientDao dao) {
        this.dao = dao;
    }

    public Integer addEntityClient(ClientEntity clientEntity) {
        Dao<ClientEntity, Integer> clientDao = this.dao.getClientDao();
        try {
            clientDao.create(clientEntity);
            return clientEntity.getClientId();
        } catch (SQLException e) {
            log.error("Cannot add Client entity " + e.getLocalizedMessage());
            return null;
        }
    }

    public ClientEntity findEntityAccountById(Integer id) {
        Dao<ClientEntity, Integer> clientDao = this.dao.getClientDao();
        ClientEntity clientEntity;
        try {
            clientEntity = clientDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Client entity " + e.getLocalizedMessage());
            return null;
        }
        return clientEntity;
    }

    public List<ClientEntity> findAllEntityAccounts() {
        Dao<ClientEntity, Integer> clientDao = this.dao.getClientDao();
        List<ClientEntity> clientEntityList;
        try {
            clientEntityList = clientDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Client entity" + e.getLocalizedMessage());
            return Collections.emptyList();
        }
        return clientEntityList;
    }
}
