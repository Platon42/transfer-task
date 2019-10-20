package mercy.digital.transfer.service.balance;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.balance.BalanceDao;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;

import java.sql.SQLException;

@Slf4j
public class BalanceServiceImpl implements BalanceService {

    @Inject
    private BalanceDao balanceDao;

    public void updateClientBalance (BalanceEntity balanceEntity) {
        Dao<BalanceEntity, Integer> balanceDao = this.balanceDao.getBalanceDao();
        try {
            balanceDao.create(balanceEntity);
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
