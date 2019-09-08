package mercy.digital.transfer.facade;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.presentation.account.AddAccount;
import mercy.digital.transfer.presentation.account.GetAccount;
import mercy.digital.transfer.service.AccountService;
import mercy.digital.transfer.service.BeneficiaryService;
import mercy.digital.transfer.service.ClientService;

public class ClientFacadeImpl implements ClientFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();

    private final ClientService clientService;
    private final AccountService accountService;
    private final BeneficiaryService beneficiaryService;

    @Inject
    public ClientFacadeImpl (ClientService clientService, AccountService accountService, BeneficiaryService beneficiaryService) {
        this.accountService = accountService;
        this.clientService = clientService;
        this.beneficiaryService = beneficiaryService;
    }
    public void addAccount(AddAccount account) {
        AccountEntity accountEntity = this.mapper.map(account, AccountEntity.class);
        accountService.addAccount(accountEntity);
    }

}
