package mercy.digital.transfer.facade;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.service.client.ClientAccountService;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.service.client.ClientService;

@Slf4j
@Singleton
public class ClientFacadeImpl implements ClientFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();

    private final ClientService clientService;
    private final ClientAccountService clientAccountService;
    private final BeneficiaryService beneficiaryService;

    @Inject
    public ClientFacadeImpl (ClientService clientService, ClientAccountService clientAccountService, BeneficiaryService beneficiaryService) {
        this.clientAccountService = clientAccountService;
        this.clientService = clientService;
        this.beneficiaryService = beneficiaryService;
    }

    public void addClient(AddClient client) {
        log.info(client.getFirstName());
        ClientEntity clientEntity = this.mapper.map(client, ClientEntity.class);
        clientService.addEntityClient(clientEntity);
    }


}
