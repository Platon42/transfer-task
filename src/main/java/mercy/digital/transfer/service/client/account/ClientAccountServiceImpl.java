package mercy.digital.transfer.service.client.account;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.client.account.ClientAccountDao;
import mercy.digital.transfer.domain.ClientAccountEntity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ClientAccountServiceImpl implements ClientAccountService {

    private final ClientAccountDao dao;

    @Inject
    public ClientAccountServiceImpl(ClientAccountDao dao) {
        this.dao = dao;
    }

    public Integer addClientEntityAccount(ClientAccountEntity accountEntity) {
        Dao<ClientAccountEntity, Integer> clientAccountDao = this.dao.getAccountDao();
        try {
            if (!accountEntity.getAccountNo().toString().startsWith("101")) return -1;
            clientAccountDao.create(accountEntity);
            return accountEntity.getClientAccountId();
        } catch (SQLException e) {
            log.error("Cannot add Client Account entity " + e.getLocalizedMessage());
        }
        return null;
    }

    public Integer updateColumnClientAccount(Integer clientAccountId, String columnName, String value) {
        Dao<ClientAccountEntity, Integer> clientAccountDao = this.dao.getAccountDao();
        UpdateBuilder<ClientAccountEntity, Integer> updateBuilder = clientAccountDao.updateBuilder();
        try {
            updateBuilder.updateColumnValue(columnName, value).where().idEq(clientAccountId);
            updateBuilder.update();
            return clientAccountId;
        } catch (SQLException e) {
            log.error("Cannot update Client Account entity " + e.getLocalizedMessage());
            return null;
        }
    }

    public ClientAccountEntity findClientEntityAccountById(Integer id) {
        Dao<ClientAccountEntity, Integer> clientAccountDao = this.dao.getAccountDao();
        ClientAccountEntity accountEntity;
        try {
            accountEntity = clientAccountDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Client Account entity" + e.getLocalizedMessage());
            return null;
        }
        return accountEntity;
    }

    public ClientAccountEntity findClientEntityAccountByAccountNo(Integer accountNo) {
        Dao<ClientAccountEntity, Integer> clientAccountDao = this.dao.getAccountDao();
        QueryBuilder<ClientAccountEntity, Integer> queryBuilder = clientAccountDao.queryBuilder();
        ClientAccountEntity accountEntity;
        try {
            PreparedQuery<ClientAccountEntity> accountNoQuery =
                    queryBuilder.where().eq("ACCOUNT_NO", accountNo).prepare();
            accountEntity = clientAccountDao.query(accountNoQuery).get(0);

        } catch (IndexOutOfBoundsException | SQLException e) {
            log.error("Cannot find by account No " + accountNo + " Client Account entity" + e.getLocalizedMessage());
            return null;
        }
        return accountEntity;
    }

    public List<ClientAccountEntity> findAllEntityClientAccounts() {
        Dao<ClientAccountEntity, Integer> clientAccountDao = this.dao.getAccountDao();
        List<ClientAccountEntity> accountEntityList;
        try {
            accountEntityList = clientAccountDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Client Account entity" + e.getLocalizedMessage());
            return Collections.emptyList();
        }
        return accountEntityList;

    }

}
