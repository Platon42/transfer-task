package mercy.digital.transfer.test;

import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;

public class RefillBalanceService implements mercy.digital.transfer.service.transaction.refill.RefillBalanceService {
    @Override
    public TransactionStatus refillBalance(int clientAccountNo, Double amount, CurrencyCode chargeCurrency) {
        return null;
    }
}
