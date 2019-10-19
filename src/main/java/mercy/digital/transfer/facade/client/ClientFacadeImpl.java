package mercy.digital.transfer.facade.client;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;

@Slf4j
@Singleton
public class ClientFacadeImpl implements ClientFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

    private final ClientService clientService;
    private final ClientAccountService clientAccountService;

    @Inject
    public ClientFacadeImpl(ClientService clientService, ClientAccountService clientAccountService) {
        this.clientAccountService = clientAccountService;
        this.clientService = clientService;
    }

    public void addClient(AddClient client) {

        try {
            ClientEntity clientEntity = this.mapper.map(client, ClientEntity.class);
            clientService.addEntityClient(clientEntity);
        } catch (Exception e) {

        }

    }


}
