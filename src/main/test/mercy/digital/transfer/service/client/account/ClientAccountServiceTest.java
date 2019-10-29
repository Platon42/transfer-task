package mercy.digital.transfer.service.client.account;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.Utils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@IncludeModule(AccountFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientAccountServiceTest {

    private static final Integer CLIENT_ID = 1;
    private static final Integer CLIENT_ACCOUNT_ID = 1;
    private static final Integer ACCOUNT_NO = 101;
    private static ClientEntity clientEntityStub = new ClientEntity();
    private static ClientAccountEntity clientAccountEntityStub = new ClientAccountEntity();
    private static ClientAccountEntity actualClientAccountEntity;
    private static ClientEntity actualClientEntity;

    static {
        Utils.setProperties(Environment.TEST);
        Utils.startDb(Environment.TEST);
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
        clientEntityStub.setBirthday(new Date(19082019));
    }

    @Test
    @Order(1)
    void addClient() {

        Integer actualClientId;
        actualClientId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(CLIENT_ID, actualClientId, "Check addEntityClient");

        actualClientEntity = clientService.findEntityAccountById(actualClientId);
        Assertions.assertEquals(CLIENT_ID, actualClientEntity.getClientId(), "check findEntityAccountById");

    }

    @Test
    @Order(2)
    void addClientEntityAccount() {

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

    }

    @Test
    @Order(3)
    void findClientEntityAccountById() {
        actualClientAccountEntity = clientAccountService.findClientEntityAccountById(CLIENT_ID);
        Assertions.assertEquals(CLIENT_ACCOUNT_ID, actualClientAccountEntity.getClientAccountId());
    }

    @Test
    @Order(4)
    void findClientEntityAccountByAccountNo() {
        actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO);
        Assertions.assertEquals(ACCOUNT_NO, actualClientAccountEntity.getAccountNo());
    }

    @Test
    @Order(5)
    void updateColumnClientAccount() {
        String columnName = "BALANCE";
        String value = "100.0";

        actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO);
        Double balanceBefore = actualClientAccountEntity.getBalance();

        //update
        clientAccountService.updateColumnClientAccount(CLIENT_ACCOUNT_ID, columnName, value);

        actualClientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO);
        Double actualBalance = actualClientAccountEntity.getBalance();

        Assertions.assertNotEquals(balanceBefore, actualBalance);
        Assertions.assertEquals(Double.parseDouble(value), actualBalance);

    }

    @Test
    void updateClientAccount() {
        //actualClientAccountEntity = clientAccountService.updateClientAccount();
    }

    @Test
    void findAllEntityClientAccounts() {
    }
}