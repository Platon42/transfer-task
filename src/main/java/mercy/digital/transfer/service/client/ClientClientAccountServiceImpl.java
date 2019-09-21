package mercy.digital.transfer.service.client;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.ClientAccountDao;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.service.client.ClientAccountService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class ClientClientAccountServiceImpl implements ClientAccountService {

    private ClientAccountDao clientAccountDao;

    @Inject
    ClientClientAccountServiceImpl(ClientAccountDao clientAccountDao) {
        this.clientAccountDao = clientAccountDao;
    }

    public void addEntityAccount(ClientAccountEntity accountEntity) {
        Dao<ClientAccountEntity, Integer> accountDao = this.clientAccountDao.getAccountDao();
        try {
            accountDao.create(accountEntity);
        } catch (SQLException e) {
            log.error("Cannot add Account entity" + e.getLocalizedMessage());
        }
    }

    public ClientAccountEntity findEntityAccountById(Integer id) {
        Dao<ClientAccountEntity, Integer> accountDao = this.clientAccountDao.getAccountDao();
        ClientAccountEntity accountEntity = null;
        try {
            accountEntity = accountDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Account entity" + e.getLocalizedMessage());
        }
        return accountEntity;
    }

    public List<ClientAccountEntity> findAllEntityAccounts() {
        Dao<ClientAccountEntity, Integer> accountDao = this.clientAccountDao.getAccountDao();
        List<ClientAccountEntity> accountEntityList = null;
        try {
            accountEntityList = accountDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Account entity" + e.getLocalizedMessage());
        }
        return accountEntityList;

    }

}
