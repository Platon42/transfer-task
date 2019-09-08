package mercy.digital.transfer.service.impl;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.AccountDao;
import mercy.digital.transfer.dao.ClientDao;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.service.ClientService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;

    @Inject
    ClientServiceImpl (ClientDao accountDao) {
        this.clientDao = accountDao;
    }

    public void addClient(ClientEntity clientEntity) {
        Dao<ClientEntity, Integer> clientDao = this.clientDao.getClientDao();
        try {
            clientDao.create(clientEntity);
        } catch (SQLException e) {
            log.error("Cannot add Client entity" + e.getLocalizedMessage());
        }
    }

    public ClientEntity findAccountById(Integer id) {
        Dao<ClientEntity, Integer> clientDao = this.clientDao.getClientDao();
        ClientEntity clientEntity = null;
        try {
            clientEntity = clientDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Client entity" + e.getLocalizedMessage());
        }
        return clientEntity;
    }

    public List<ClientEntity> findAllAccounts() {
        Dao<ClientEntity, Integer> clientDao = this.clientDao.getClientDao();
        List<ClientEntity> clientEntityList = null;
        try {
            clientEntityList = clientDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Client entity" + e.getLocalizedMessage());
        }
        return clientEntityList;
    }
}
