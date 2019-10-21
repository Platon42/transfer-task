package mercy.digital.transfer.service.transaction;

import mercy.digital.transfer.domain.TransactionEntity;

import java.util.List;

public interface TransactionService {
    void addEntityTransaction(TransactionEntity transactionEntity);
    TransactionEntity findEntityTransactionById(Integer id);
    List<TransactionEntity> findAllTransactions();
    List<TransactionEntity> findEntityTransactionByAccountNo (Integer accountNo);
}
