package mercy.digital.transfer.service.transaction.refill;

import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;

public interface RefillBalanceService {
    TransactionStatus refillBalance(int clientAccountNo, Double amount, CurrencyCode chargeCurrency);
}
