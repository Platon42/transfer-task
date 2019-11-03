package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.utils.db.H2Utils;
import mercy.digital.transfer.utils.prop.Environment;
import mercy.digital.transfer.utils.prop.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Date;
import java.util.List;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(AccountFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientServiceTest {

    private static final Integer CLIENT_ID = 1;
    private static final String FIRST_NAME = "Ivan";
    private static ClientEntity clientEntityStub = new ClientEntity();

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    private ClientService clientService;

    @BeforeAll
    static void setClient() {
        clientEntityStub.setFirstName(FIRST_NAME);
        clientEntityStub.setLastName("Smirnov");
        clientEntityStub.setResidentCountry("RU");
        clientEntityStub.setSex(1);
        clientEntityStub.setBirthday(new Date(19082019));
    }

    @Test
    @Order(1)
    void findAllEntityAccountsException() {
        List<ClientEntity> allEntityAccounts = clientService.findAllEntityAccounts();
        Assertions.assertTrue(allEntityAccounts.isEmpty());
    }

    @Test
    @Order(2)
    void addEntityClient() {
        Integer actualClientId;
        actualClientId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(CLIENT_ID, actualClientId);
    }
    @Test
    @Order(3)
    void addEntityClientException() {
        ClientEntity clientEntity = new ClientEntity();
        Assertions.assertNull(clientService.addEntityClient(clientEntity));
    }

    @Test
    @Order(4)
    void findEntityAccountById() {
        ClientEntity actualAccount = clientService.findEntityAccountById(CLIENT_ID);
        Integer actualClientId = actualAccount.getClientId();
        Assertions.assertEquals(CLIENT_ID, actualClientId);
    }

    @Test
    @Order(5)
    void findEntityAccountByIdException() {
        ClientEntity actualAccount = clientService.findEntityAccountById(2);
        Assertions.assertNull(actualAccount);
    }

    @Test
    @Order(6)
    void findAllEntityAccounts() {
        List<ClientEntity> allEntityAccounts = clientService.findAllEntityAccounts();
        String actualFirstName = allEntityAccounts.get(0).getFirstName();
        Assertions.assertEquals(FIRST_NAME, actualFirstName);
    }

}