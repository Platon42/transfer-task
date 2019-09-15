package mercy.digital.transfer.facade;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.presentation.account.AddAccount;
import mercy.digital.transfer.service.AccountService;
import mercy.digital.transfer.service.BalanceService;
import mercy.digital.transfer.service.TransactionService;
import mercy.digital.transfer.service.TransferService;

public class AccountFacadeImpl implements AccountFacade {
    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();

    @Inject
    private TransactionService transactionService;
    @Inject
    private BalanceService balanceService;
    @Inject
    private TransferService transferService;
    @Inject
    private AccountService accountService;


    public void addAccount(AddAccount account) {
        AccountEntity accountEntity = this.mapper.map(account, AccountEntity.class);
        accountService.addEntityAccount(accountEntity);
    }

}
