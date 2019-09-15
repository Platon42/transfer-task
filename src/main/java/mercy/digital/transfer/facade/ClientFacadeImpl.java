package mercy.digital.transfer.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.service.AccountService;
import mercy.digital.transfer.service.BeneficiaryService;
import mercy.digital.transfer.service.ClientService;

@Slf4j
@Singleton
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

    public void addClient(AddClient client) {
        log.info(client.getFirstName());
        ClientEntity clientEntity = this.mapper.map(client, ClientEntity.class);
        clientService.addEntityClient(clientEntity);
    }


}
