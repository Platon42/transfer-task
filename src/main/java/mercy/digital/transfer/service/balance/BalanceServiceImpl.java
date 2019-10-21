package mercy.digital.transfer.service.balance;

import com.google.inject.Inject;
import com.google.inject.internal.cglib.proxy.$FixedValue;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.NonNull;
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
import org.h2.mvstore.tx.Transaction;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Slf4j
public class BalanceServiceImpl implements BalanceService {

    @Inject
    private BalanceDao balanceDao;

    @Inject
    private TransactionService transactionService;

    @Inject
    private ClientAccountService clientAccountService;

    private void addClientBalance(BalanceEntity balanceEntity) {
        Dao<BalanceEntity, Integer> balanceDao = this.balanceDao.getBalanceDao();
        try {
            balanceDao.create(balanceEntity);
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
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

    public void refillClientAccount(
            ClientAccountEntity clientAccountEntity,
            TransactionEntity transactionEntity,
            BalanceEntity balanceEntity,
            int clientAccountNo,
            Double transactionAmount,
            Double refillAmount,
            Double clientBalance,
            CurrencyCode transactionCurrency) {

        Double newBalance = clientBalance + refillAmount;

        transactionEntity.setSourceAccountNo(clientAccountNo);
        transactionEntity.setCreatedAt(Timestamp.from(Instant.now()));
        transactionEntity.setCurrency(transactionCurrency.name());
        transactionEntity.setAmount(transactionAmount);
        transactionEntity.setTransactionType(TransactionType.REFILL.name());

        transactionService.addEntityTransaction(transactionEntity);

        balanceEntity.setClientAccountByAccountId(clientAccountEntity);
        balanceEntity.setBeforeBalance(clientBalance);
        balanceEntity.setPastBalance(newBalance);
        balanceEntity.setTransactionByTransactionId(transactionEntity);
        addClientBalance(balanceEntity);

        clientAccountService.updateColumnClientAccount(clientAccountEntity.getClientAccountId(), "BALANCE", newBalance.toString());

    }

    private void setTransactionEntity (TransactionEntity transactionEntity ) {

    }

    public void transferFunds(ClientAccountEntity clientSenderAccountEntity,
                              TransactionEntity transactionEntity,
                              BalanceEntity balanceSenderEntity,
                              BeneficiaryAccountEntity beneficiaryAccountEntity,
                              int clientAccountNo, int beneficiaryAccountNo, Double reqAmount, CurrencyCode targetCurrency) {


        //when beneficiary our client, needed change their balance
        if (beneficiaryAccountEntity.getClient()) {

            ClientAccountEntity clientReceiverAccountEntity =
                    clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountNo);

            Double oldBalanceReceiver = clientReceiverAccountEntity.getBalance();
            if (oldBalanceReceiver == null) {
                oldBalanceReceiver = 0.0;
            }

            Double newBalanceReceiver = oldBalanceReceiver + reqAmount;
            BalanceEntity balanceReceiverAccountEntity = new BalanceEntity();
            TransactionEntity transactionReceiverEntity = new TransactionEntity();

            transactionReceiverEntity.setSourceAccountNo(clientAccountNo);
            transactionReceiverEntity.setTargetAccountNo(beneficiaryAccountNo);
            transactionReceiverEntity.setAmount(reqAmount);
            transactionReceiverEntity.setCurrency(targetCurrency.name());
            transactionReceiverEntity.setTransactionType(TransactionType.REFILL.name());
            transactionReceiverEntity.setCreatedAt(Timestamp.from(Instant.now()));

            transactionService.addEntityTransaction(transactionEntity);
            clientAccountService.updateColumnClientAccount(clientReceiverAccountEntity.getClientAccountId(), "BALANCE",
                    newBalanceReceiver.toString());

            Objects.requireNonNull(balanceReceiverAccountEntity).setClientAccountByAccountId(clientReceiverAccountEntity);

            balanceReceiverAccountEntity.setBeforeBalance(oldBalanceReceiver);
            balanceReceiverAccountEntity.setPastBalance(newBalanceReceiver);
            balanceReceiverAccountEntity.setTransactionByTransactionId(transactionEntity);
            addClientBalance(balanceReceiverAccountEntity);
        }

        Double oldBalanceSender = clientSenderAccountEntity.getBalance();
        if (oldBalanceSender == null) oldBalanceSender = 0.0;

        Double newBalanceSender = oldBalanceSender - reqAmount;

        clientAccountService.updateColumnClientAccount(clientSenderAccountEntity.getClientAccountId(), "BALANCE",
                newBalanceSender.toString());

        transactionEntity.setSourceAccountNo(clientAccountNo);
        transactionEntity.setTargetAccountNo(beneficiaryAccountNo);
        transactionEntity.setAmount(reqAmount);
        transactionEntity.setCurrency(targetCurrency.name());
        transactionEntity.setTransactionType(TransactionType.FUNDS_TRANSFER.name());
        transactionEntity.setCreatedAt(Timestamp.from(Instant.now()));

        transactionService.addEntityTransaction(transactionEntity);

        balanceSenderEntity.setClientAccountByAccountId(clientSenderAccountEntity);

        balanceSenderEntity.setBeforeBalance(oldBalanceSender);
        balanceSenderEntity.setPastBalance(newBalanceSender);
        balanceSenderEntity.setTransactionByTransactionId(transactionEntity);
        addClientBalance(balanceSenderEntity);

    }
}
