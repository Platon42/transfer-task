package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(AccountFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientAccountServiceTest {

    private static final Integer CLIENT_ID = 1;
    private static final Integer CLIENT_ACCOUNT_ID = 1;

    private static final Integer ACCOUNT_NO = 101;
    private static final Integer WRONG_CLIENT_ACCOUNT_NO = 111111;

    private static ClientEntity clientEntityStub = new ClientEntity();
    private static ClientAccountEntity clientAccountEntityStub = new ClientAccountEntity();

    private static ClientAccountEntity actualClientAccountEntity;
    private static ClientEntity actualClientEntity;

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    private ClientAccountService clientAccountService;
    @Inject
    private ClientService clientService;

    @BeforeAll
    static void setClient() {
        clientEntityStub.setFirstName("Ivan");
        clientEntityStub.setLastName("Smirnov");
        clientEntityStub.setResidentCountry("RU");
        clientEntityStub.setSex(1);
        clientEntityStub.setResidentCountry("Russia");
        clientEntityStub.setMiddleName("Vlad");
        clientEntityStub.setBirthday(new Date(19082019));
    }

    @Test
    @Order(1)
    void findAllEntityClientAccountsException() {
        List<ClientAccountEntity> allEntityClientAccounts = clientAccountService.findAllEntityClientAccounts();
        Assertions.assertTrue(allEntityClientAccounts.isEmpty());
    }

    @Test
    @Order(2)
    void addClient() {

        Integer actualClientId;
        actualClientId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(CLIENT_ID, actualClientId, "Check addEntityClient");

        actualClientEntity = clientService.findEntityAccountById(actualClientId);
        Assertions.assertEquals(CLIENT_ID, actualClientEntity.getClientId(), "check findEntityAccountById");

    }

    @Test
    @Order(3)
    void addClientAndNotClientEntityAccount() {

        Integer actualClientAccountId;

        clientAccountEntityStub.setAccountNo(ACCOUNT_NO);
        clientAccountEntityStub.setBalance(0.0);
        clientAccountEntityStub.setCreatedAt(Timestamp.from(Instant.now()));
        clientAccountEntityStub.setCurrency(CurrencyCode.RUB.name());
        clientAccountEntityStub.setClientByClientId(actualClientEntity);

        //add account
        actualClientAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        //check id
        Assertions.assertEquals(CLIENT_ACCOUNT_ID, actualClientAccountId, "check addClientEntityAccount");
        // check wrong flag
        clientAccountEntityStub.setAccountNo(WRONG_CLIENT_ACCOUNT_NO);
        Integer wrongFlag = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        Assertions.assertEquals(-1, wrongFlag);
    }

    @Test
    @Order(4)
    void addClientEntityAccountException() {

        ClientAccountEntity clientAccountEntity = new ClientAccountEntity();
        clientAccountEntity.setAccountNo(101222);
        Integer actualClientAccountId = clientAccountService.addClientEntityAccount(clientAccountEntity);

        Assertions.assertNull(actualClientAccountId);
    }


    @Test
    @Order(5)
    void findClientEntityAccountById() {
        actualClientAccountEntity = clientAccountService.findClientEntityAccountById(CLIENT_ID);
        Assertions.assertEquals(CLIENT_ACCOUNT_ID, actualClientAccountEntity.getClientAccountId());
    }

    @Test
    @Order(6)
    void findClientEntityAccountByIdException() {
        actualClientAccountEntity = clientAccountService.findClientEntityAccountById(323323);
        Assertions.assertNull(actualClientAccountEntity);
    }

    @Test
    @Order(7)
    void findClientEntityAccountByAccountNo() {
        actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO);
        Assertions.assertEquals(ACCOUNT_NO, actualClientAccountEntity.getAccountNo());
    }

    @Test
    @Order(8)
    void findClientEntityAccountByAccountNoException() {
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(323323);
        });
    }

    @Test
    @Order(9)
    void updateColumnClientAccount() {

        String columnName = "BALANCE";
        String value = "100.0";

        actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO);
        Double balanceBefore = actualClientAccountEntity.getBalance();

        //update
        clientAccountService.updateColumnClientAccount(CLIENT_ACCOUNT_ID, columnName, value);

        actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO);
        Double actualBalance = actualClientAccountEntity.getBalance();

        //Assertions.assertNotEquals(balanceBefore, actualBalance);
        Assertions.assertEquals(Double.parseDouble(value), actualBalance);

    }


    @Test
    @Order(10)
    void updateColumnClientAccountException() {

        String columnName = "BALANCE";
        String value = "100.0_EXCEPTION";

        actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO);

        Integer id = clientAccountService.updateColumnClientAccount(CLIENT_ACCOUNT_ID, columnName, value);
        Assertions.assertNull(id);

    }


    @Test
    @Order(11)
    void findAllEntityClientAccounts() {

        clientAccountEntityStub = new ClientAccountEntity();

        clientAccountEntityStub.setAccountNo(1012);
        clientAccountEntityStub.setBalance(0.0);
        clientAccountEntityStub.setCreatedAt(Timestamp.from(Instant.now()));
        clientAccountEntityStub.setCurrency(CurrencyCode.RUB.name());
        clientAccountEntityStub.setClientByClientId(actualClientEntity);

        //add account
        Integer actualClientAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        Assertions.assertEquals(3, actualClientAccountId);
        List<ClientAccountEntity> allEntityClientAccounts = clientAccountService.findAllEntityClientAccounts();
        Assertions.assertEquals(1012, allEntityClientAccounts.get(1).getAccountNo());

    }
}