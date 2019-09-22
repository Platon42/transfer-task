package mercy.digital.transfer.facade.account;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transfer.TransactionService;
import mercy.digital.transfer.service.transfer.TransferService;

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

    @Inject
    private ClientService clientService;

    public void addClientAccount(AddClientAccount clientAccount) {

        ClientAccountEntity accountEntity;
        ClientEntity accountById = clientService.findEntityAccountById(clientAccount.getGetClient().getClientId());
        if (accountById != null) {
            accountEntity = this.mapper.map(clientAccount, ClientAccountEntity.class);
            accountEntity.setClientByClientId(accountById);
            clientAccountService.addClientEntityAccount(accountEntity);
        }
    }
}
