package mercy.digital.transfer.service.transaction;

public interface TransactionService {
    TransactionStatus doTransfer(
            int clientAccountNo, //accountNo
            int beneficiaryAccountNo, //accountNo
            Double reqAmount,
            TransactionType type,
            CurrencyCode transferCurrency);
}
