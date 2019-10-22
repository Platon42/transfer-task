package mercy.digital.transfer.service.transaction.transfer;

import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;

public interface TransferService {
    TransactionStatus doTransfer(
            int clientAccountNo, //accountNo
            int beneficiaryAccountNo, //accountNo
            Double reqAmount,
            CurrencyCode transferCurrency);
}
