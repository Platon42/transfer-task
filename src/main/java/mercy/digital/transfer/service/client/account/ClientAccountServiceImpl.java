package mercy.digital.transfer.service.client.account;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.client.account.ClientAccountDao;
import mercy.digital.transfer.domain.ClientAccountEntity;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class ClientAccountServiceImpl implements ClientAccountService {

    private ClientAccountDao clientAccountDao;

    @Inject
    ClientAccountServiceImpl(ClientAccountDao clientAccountDao) {
        this.clientAccountDao = clientAccountDao;
    }

    public void addClientEntityAccount(ClientAccountEntity accountEntity) {
        Dao<ClientAccountEntity, Integer> accountDao = this.clientAccountDao.getAccountDao();
        try {
            accountDao.create(accountEntity);
        } catch (SQLException e) {
            log.error("Cannot add Client Account entity " + e.getLocalizedMessage());
        }
    }

    public ClientAccountEntity findClientEntityAccountById(Integer id) {
        Dao<ClientAccountEntity, Integer> accountDao = this.clientAccountDao.getAccountDao();
        ClientAccountEntity accountEntity = null;
        try {
            accountEntity = accountDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Client Account entity" + e.getLocalizedMessage());
        }
        return accountEntity;
    }

    public List<ClientAccountEntity> findAllEntityClientAccounts() {
        Dao<ClientAccountEntity, Integer> accountDao = this.clientAccountDao.getAccountDao();
        List<ClientAccountEntity> accountEntityList = null;
        try {
            accountEntityList = accountDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Client Account entity" + e.getLocalizedMessage());
        }
        return accountEntityList;

    }

}
