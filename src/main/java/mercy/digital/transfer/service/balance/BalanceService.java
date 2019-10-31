package mercy.digital.transfer.service.balance;

import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;

public interface BalanceService {

    Integer setBalanceEntity(
            ClientAccountEntity clientAccountEntity,
            BalanceEntity balanceEntity,
            TransactionEntity transactionEntity,
            Double oldBalance,
            Double newBalance);

}
