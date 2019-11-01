package mercy.digital.transfer.facade.client;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.response.ResponseServiceBuilder;
import mercy.digital.transfer.service.client.ClientService;

@Slf4j
public class ClientFacadeImpl implements ClientFacade {

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapper = mapperFactory.getMapperFacade();
    private final ResponseModel responseModel = new ResponseModel();

    private final ClientService clientService;

    @Inject
    public ClientFacadeImpl(ClientService clientService) {
        this.clientService = clientService;
    }

    public ResponseModel addClient(AddClient client) {
        ClientEntity clientEntity = this.mapper.map(client, ClientEntity.class);
        Integer clientId = clientService.addEntityClient(clientEntity);
        ResponseServiceBuilder responseServiceBuilder =
                new ResponseServiceBuilder(responseModel, "addClient");
        if (clientId != null) {
            responseServiceBuilder.withMessage("Success client added");
            responseServiceBuilder.withAdditional("clientId:" + clientId);
            responseServiceBuilder.withStatus(0);
        } else {
            responseServiceBuilder.withMessage("Common error occurred, see log for details");
            responseServiceBuilder.withStatus(-1);
        }
        responseServiceBuilder.build();
        return responseModel;
    }
}
