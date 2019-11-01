package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.facade.client.ClientFacade;
import mercy.digital.transfer.facade.client.ClientFacadeImpl;
import mercy.digital.transfer.module.ClientFacadeModule;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(ClientFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ClientFacadeTest {

    private AddClient addClientStub;

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }
    @Inject
    private ClientService clientService;

    private ClientFacade clientFacade;

    @BeforeAll
    void initTest() {
        addClientStub = new AddClient();
        clientService = Mockito.mock(ClientService.class);
        clientFacade = new ClientFacadeImpl(clientService);
    }

    @Test
    @Order(1)
    void addClientSuccess() {

        when(clientService.addEntityClient(any())).thenReturn(1);
        clientFacade.addClient(addClientStub);
        // exercise
        ResponseModel actualResponseModel = clientFacade.addClient(addClientStub);
        // verify
        ResponseModel expectedResponseModel = new ResponseModel();
        expectedResponseModel.setMessage("Success client added");
        Assertions.assertEquals(actualResponseModel.getMessage(), expectedResponseModel.getMessage());
    }

    @Test
    @Order(2)
    void addClientFail() {

        when(clientService.addEntityClient(any())).thenReturn(null);
        clientFacade.addClient(addClientStub);
        // exercise
        ResponseModel actualResponseModel = clientFacade.addClient(addClientStub);
        // verify
        ResponseModel expectedResponseModel = new ResponseModel();
        expectedResponseModel.setMessage("Common error occurred, see log for details");
        Assertions.assertEquals(actualResponseModel.getMessage(), expectedResponseModel.getMessage());
    }

}
