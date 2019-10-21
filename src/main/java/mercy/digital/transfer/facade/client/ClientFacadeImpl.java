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

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class ClientFacadeImpl implements ClientFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

    @Inject
    private ClientService clientService;

    public ResponseModel addClient(AddClient client) {
        ClientEntity clientEntity = this.mapper.map(client, ClientEntity.class);
        Integer id = clientService.addEntityClient(clientEntity);

        responseModel.setService("addClient");
        responseModel.setDateTime(LocalDateTime.now());

        if (id != null) {
            responseModel.setMessage("Success client added");
            responseModel.setId(id);
            return responseModel;
        } else {
            responseModel.setErrorMessage("Common error occurred, see log for details");
            return responseModel;
        }
    }
}
