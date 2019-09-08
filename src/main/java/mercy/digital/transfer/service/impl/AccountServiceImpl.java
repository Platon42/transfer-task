package mercy.digital.transfer.service.impl;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.AccountDao;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.service.AccountService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class AccountServiceImpl implements AccountService {

    private AccountDao accountDao;

    @Inject
    AccountServiceImpl (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void addAccount (AccountEntity accountEntity) {
        Dao<AccountEntity, Integer> accountDao = this.accountDao.getAccountDao();
        try {
            accountDao.create(accountEntity);
        } catch (SQLException e) {
            log.error("Cannot add Account entity" + e.getLocalizedMessage());
        }
    }

    public AccountEntity findAccountById (Integer id) {
        Dao<AccountEntity, Integer> accountDao = this.accountDao.getAccountDao();
        AccountEntity accountEntity = null;
        try {
            accountEntity = accountDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Account entity" + e.getLocalizedMessage());
        }
        return accountEntity;
    }

    public List<AccountEntity> findAllAccounts ()  {
        Dao<AccountEntity, Integer> accountDao = this.accountDao.getAccountDao();
        List<AccountEntity> accountEntityList = null;
        try {
            accountEntityList = accountDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Account entity" + e.getLocalizedMessage());
        }
        return accountEntityList;

    }

}
