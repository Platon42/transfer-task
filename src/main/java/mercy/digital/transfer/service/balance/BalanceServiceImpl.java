package mercy.digital.transfer.service.balance;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.balance.BalanceDao;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.client.account.ClientAccountService;
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

    @Inject
    private ClientAccountService clientAccountService;

    private void updateClientBalance(BalanceEntity balanceEntity) {
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

        Double newBalance = clientBalance + amount;

        System.out.println("ID " + clientAccountEntity.getClientAccountId());
        clientAccountService.updateColumnClientAccount(clientAccountEntity.getClientAccountId(), "BALANCE", newBalance.toString());

        transactionEntity.setSourceAccountNo(clientAccountNo);
        transactionEntity.setCreatedAt(Timestamp.from(Instant.now()));
        transactionEntity.setCurrency(chargeCurrency.name());
        transactionEntity.setAmount(newBalance);

        transactionEntity.setTransactionType(TransactionType.REFILL.name());
        transactionService.addEntityTransaction(transactionEntity);

        balanceEntity.setClientAccountByAccountId(clientAccountEntity);
        balanceEntity.setBeforeBalance(clientBalance);
        balanceEntity.setPastBalance(newBalance);
        balanceEntity.setTransactionByTransactionId(transactionEntity);
        updateClientBalance(balanceEntity);

    }

    @Override
    public void transferFunds(ClientAccountEntity clientAccountEntity, TransactionEntity transactionEntity, BalanceEntity balanceEntity, BeneficiaryAccountEntity beneficiaryAccountEntity, int clientAccountNo, int beneficiaryAccountNo, Double reqAmount, CurrencyCode targetCurrency) {

    }

    public void transferFunds(

            ClientAccountEntity clientAccountEntity,
            BeneficiaryAccountEntity beneficiaryAccountEntity,

            TransactionEntity transactionEntity,
            BalanceEntity balanceEntity,
            int clientAccountNo,
            int beneficiaryAccountNo,
            Double reqAmount,
            CurrencyCode targetCurrency) {

        if (beneficiaryAccountEntity.getClient()) {

        }
        Double oldBalance = clientAccountEntity.getBalance();
        Double newBalance = oldBalance - reqAmount;
        clientAccountService.updateColumnClientAccount(clientAccountEntity.getClientAccountId(), "BALANCE", newBalance.toString());

        transactionEntity.setSourceAccountNo(clientAccountNo);
        transactionEntity.setTargetAccountNo(beneficiaryAccountNo);
        transactionEntity.setAmount(reqAmount);
        transactionEntity.setCurrency(targetCurrency.name());
        transactionEntity.setTransactionType(TransactionType.FUNDS_TRANSFER.name());
        transactionEntity.setCreatedAt(Timestamp.from(Instant.now()));

        transactionService.addEntityTransaction(transactionEntity);

        balanceEntity.setClientAccountByAccountId(clientAccountEntity);

        balanceEntity.setBeforeBalance(oldBalance);
        balanceEntity.setPastBalance(newBalance);
        balanceEntity.setTransactionByTransactionId(transactionEntity);
        updateClientBalance(balanceEntity);

    }
}
