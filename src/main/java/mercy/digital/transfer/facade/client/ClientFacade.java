package mercy.digital.transfer.facade.client;

import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;


public interface ClientFacade {
    ResponseModel addClient(AddClient client);
}