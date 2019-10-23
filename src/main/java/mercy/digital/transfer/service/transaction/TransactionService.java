package mercy.digital.transfer.service.transaction;

import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionType;

import java.util.List;

public interface TransactionService {
    void addEntityTransaction(TransactionEntity transactionEntity);

    void setTransactionEntity(TransactionEntity transactionEntity,
                              TransactionType transactionType,
                              Double transactionAmount,
                              CurrencyCode currencyCode,
                              Integer clientAccountNo,
                              Integer beneficiaryAccountNo);
    TransactionEntity findEntityTransactionById(Integer id);
    List<TransactionEntity> findAllTransactions();

    List<TransactionEntity> findEntitiesTransactionByAccountNo(Integer accountNo);
}
