package mercy.digital.transfer.unit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Injector;
import io.javalin.Javalin;
import mercy.digital.transfer.facade.client.ClientFacade;
import mercy.digital.transfer.presentation.client.AddClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;


class ControllerTest {

    private Javalin app;
    private ObjectMapper objectMapper;

    private Injector clientFacadeInjector;
    private Injector accountFacadeInjector;
    private Injector beneficiaryFacadeInjector;

    @BeforeEach
    void setUp() {
        app = Mockito.mock(Javalin.class);
        objectMapper = Mockito.mock(ObjectMapper.class);
        clientFacadeInjector = Mockito.mock(Injector.class);
        AddClient clientStub = new AddClient();
        clientStub.setFirstName("Ivan");
    }

    @Test
    void main() {
        app.get("/client/add", ctx -> {

            AddClient actualClient = objectMapper.readValue(ctx.body(), AddClient.class);
            when(objectMapper.readValue(ctx.body(), AddClient.class)).thenReturn(actualClient);
            ClientFacade clientFacade = clientFacadeInjector.getInstance(ClientFacade.class);
            //ResponseModel responseModel = clientFacade.addClient(client);
            //ctx.result(objectMapper.writeValueAsString(responseModel));
        });
    }
}