package mercy.digital.transfer.service.balance;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.balance.BalanceDao;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionType;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

@Slf4j
public class BalanceServiceImpl implements BalanceService {

    @Inject
    private BalanceDao balanceDao;

    @Inject
    private TransactionService transactionService;


    public void updateClientBalance (BalanceEntity balanceEntity) {
        Dao<BalanceEntity, Integer> balanceDao = this.balanceDao.getBalanceDao();
        try {
            balanceDao.create(balanceEntity);
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    public void refillClientAccount(

            ClientAccountEntity clientAccountEntity,
            TransactionEntity transactionEntity,
            BalanceEntity balanceEntity,
            int clientAccountNo,
            Double amount,
            Double clientBalance,
            CurrencyCode chargeCurrency) {

        transactionEntity.setSourceAccountNo(clientAccountNo);
        transactionEntity.setCreatedAt(Timestamp.from(Instant.now()));
        transactionEntity.setCurrency(chargeCurrency.name());
        transactionEntity.setAmount(clientBalance + amount);

        transactionEntity.setTransactionType(TransactionType.REFILL.name());
        transactionService.addEntityTransaction(transactionEntity);
        ;

        balanceEntity.setClientAccountByAccountId(clientAccountEntity);
        balanceEntity.setPastBalance(amount);
        balanceEntity.setTransactionByTransactionId(transactionEntity);
        updateClientBalance(balanceEntity);

    }
}
