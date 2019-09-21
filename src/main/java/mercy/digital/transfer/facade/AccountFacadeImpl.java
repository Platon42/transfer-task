package mercy.digital.transfer.facade;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.service.client.ClientAccountService;
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
    private ClientAccountService clientAccountService;


    public void addClientAccount(AddClientAccount clientAccount) {
        ClientAccountEntity accountEntity = this.mapper.map(clientAccount, ClientAccountEntity.class);
        clientAccountService.addEntityAccount(accountEntity);
    }

}
