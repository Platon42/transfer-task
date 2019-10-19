package mercy.digital.transfer.service.balance;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;

@Slf4j
public class BalanceServiceImpl implements BalanceService {

    @Inject
    private TransactionService transactionService;

    @Inject
    private ClientAccountService clientAccountService;

    public void addBalanceEntity (){

    }
}
