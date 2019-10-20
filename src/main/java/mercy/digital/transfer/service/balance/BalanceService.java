package mercy.digital.transfer.service.balance;

import mercy.digital.transfer.domain.BalanceEntity;

public interface BalanceService {
    void updateClientBalance (BalanceEntity balanceEntity);
}
