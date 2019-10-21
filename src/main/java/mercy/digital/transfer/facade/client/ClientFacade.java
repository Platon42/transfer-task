package mercy.digital.transfer.facade.client;

import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;

import java.sql.SQLException;

public interface ClientFacade {
    ResponseModel addClient(AddClient client);
}