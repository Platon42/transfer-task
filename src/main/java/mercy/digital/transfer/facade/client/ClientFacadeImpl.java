package mercy.digital.transfer.facade.client;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.service.client.ClientService;

import java.time.LocalDateTime;

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
        Integer id = clientService.addEntityClient(clientEntity);

        responseModel.setService("addClient");
        responseModel.setDateTime(LocalDateTime.now());

        if (id != null) {
            responseModel.setMessage("Success client added");
            responseModel.setAdditional(String.valueOf(id));
            responseModel.setStatus(0);
        } else {
            responseModel.setMessage("Common error occurred, see log for details");
            responseModel.setStatus(-1);
        }
        return responseModel;
    }
}
