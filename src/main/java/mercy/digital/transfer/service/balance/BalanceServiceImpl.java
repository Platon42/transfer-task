package mercy.digital.transfer.service.balance;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.balance.BalanceDao;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.client.account.ClientAccountService;

import java.sql.SQLException;

@Slf4j
public class BalanceServiceImpl implements BalanceService {

    private final BalanceDao dao;
    private final ClientAccountService clientAccountService;

    @Inject
    public BalanceServiceImpl(BalanceDao dao, ClientAccountService clientAccountService) {
        this.dao = dao;
        this.clientAccountService = clientAccountService;
    }

    public Integer setBalanceEntity
            (ClientAccountEntity clientAccountEntity,
             BalanceEntity balanceEntity,
             TransactionEntity transactionEntity,
             Double oldBalance,
             Double newBalance) {
        try {

            Dao<BalanceEntity, Integer> balanceDao = this.dao.getBalanceDao();
            balanceEntity.setClientAccountByAccountId(clientAccountEntity);
            balanceEntity.setBeforeBalance(oldBalance);
            balanceEntity.setPastBalance(newBalance);
            balanceEntity.setTransactionByTransactionId(transactionEntity);
            balanceDao.create(balanceEntity);

            clientAccountService.updateColumnClientAccount(
                    clientAccountEntity.getClientAccountId(), "BALANCE",
                    newBalance.toString());

        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
        return balanceEntity.getBalanceId();
    }
}
