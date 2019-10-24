package mercy.digital.transfer.facade.client;

import mercy.digital.transfer.module.ClientFacadeModule;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.service.client.ClientService;
import name.falgout.jeffrey.testing.junit.guice.GuiceExtension;
import name.falgout.jeffrey.testing.junit.guice.IncludeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(GuiceExtension.class)
@IncludeModule(ClientFacadeModule.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientFacadeImplTest {

    public static final int CLIENT_ID = 10;
    private AddClient addClientStub;

    @BeforeAll
    void initTest() {
        addClientStub = new AddClient();
    }

    @Test
    void addClient() {
        // setup
        ClientService clientService = Mockito.mock(ClientService.class);
        when(clientService.addEntityClient(any())).thenReturn(CLIENT_ID);
        ClientFacadeImpl clientFacade = new ClientFacadeImpl();
        clientFacade.setClientService(clientService);

        // exercise
        ResponseModel actualResponseModel = clientFacade.addClient(addClientStub);

        // verify
        ResponseModel expectedResponseModel = new ResponseModel();
        expectedResponseModel.setId(CLIENT_ID);
        expectedResponseModel.setMessage("Success client added");
        Assertions.assertEquals(expectedResponseModel.getMessage(),actualResponseModel.getMessage());
        Assertions.assertEquals(expectedResponseModel.getId(),expectedResponseModel.getId());
    }
}