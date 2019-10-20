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

import java.sql.SQLException;

@Slf4j
@Singleton
public class ClientFacadeImpl implements ClientFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

    @Inject
    private ClientService clientService;
    @Inject
    private ClientAccountService clientAccountService;

    public void addClient(AddClient client) throws SQLException {
        ClientEntity clientEntity = this.mapper.map(client, ClientEntity.class);

        try {
            clientService.addEntityClient(clientEntity);
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
            throw new SQLException("Cannot create client Entity by client " + client.getFirstName());
        }

    }
}
