package mercy.digital.transfer.service.balance;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.balance.BalanceDao;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.client.account.ClientAccountService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class BalanceServiceImpl implements BalanceService {

    @Inject
    private BalanceDao balanceDao;

    @Inject
    private ClientAccountService clientAccountService;

    public void addClientBalance(BalanceEntity balanceEntity) {
        Dao<BalanceEntity, Integer> balanceDao = this.balanceDao.getBalanceDao();
        try {
            balanceDao.create(balanceEntity);
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public void setBalanceEntity(
            ClientAccountEntity clientAccountEntity,
            BalanceEntity balanceEntity,
            TransactionEntity transactionEntity,
            Double oldBalance,
            Double newBalance) {

        balanceEntity.setClientAccountByAccountId(clientAccountEntity);
        balanceEntity.setBeforeBalance(oldBalance);
        balanceEntity.setPastBalance(newBalance);
        balanceEntity.setTransactionByTransactionId(transactionEntity);
        addClientBalance(balanceEntity);

        clientAccountService.updateColumnClientAccount(
                clientAccountEntity.getClientAccountId(), "BALANCE",
                newBalance.toString());

    }

    private BalanceEntity findBalanceEntityByAccountId(Integer id) {
        Dao<BalanceEntity, Integer> balanceDao = this.balanceDao.getBalanceDao();
        QueryBuilder<BalanceEntity, Integer> balanceQueryBuilder = balanceDao.queryBuilder();
        BalanceEntity balanceEntity;
        try {
            PreparedQuery<BalanceEntity> balanceQuery = balanceQueryBuilder.where().eq("ACCOUNT_ID", id).prepare();
            List<BalanceEntity> balanceEntities = balanceDao.query(balanceQuery);
            if (balanceEntities.size() != 0) {
                balanceEntity = balanceEntities.get(0);
            } else {
                log.warn("Not found balance records for AccountID" + id);
                return null;
            }
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
        return balanceEntity;
    }
}
