package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import mercy.digital.transfer.utils.db.H2Utils;
import mercy.digital.transfer.utils.prop.Environment;
import mercy.digital.transfer.utils.prop.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(BeneficiaryFacadeModule.class)
@IncludeModule(AccountFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BalanceServiceTest {

    private static final Integer CLIENT_ID = 1;
    private static final Integer CLIENT_ACCOUNT_ID = 1;
    private static final Integer BALANCE_ID = 1;
    private static final Double AMOUNT = 100.0;
    private static final Double NEW_AMOUNT = 50.0;
    private static final Integer ACCOUNT_NO = 101;
    private static ClientEntity clientEntityStub = new ClientEntity();
    private static ClientAccountEntity clientAccountEntityStub = new ClientAccountEntity();
    private static BalanceEntity balanceEntity = new BalanceEntity();
    private static TransactionEntity transactionEntity = new TransactionEntity();

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    private BalanceService balanceService;

    @Inject
    private ClientService clientService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private TransactionService transactionService;

    @BeforeAll
    static void setClientAndAccount() {

        clientEntityStub.setFirstName("Ivan");
        clientEntityStub.setLastName("Smirnov");
        clientEntityStub.setResidentCountry("RU");
        clientEntityStub.setSex(1);
        clientEntityStub.setResidentCountry("Russia");
        clientEntityStub.setMiddleName("Vlad");
        clientEntityStub.setBirthday(new Date(19082019));

        clientAccountEntityStub.setAccountNo(ACCOUNT_NO);
        clientAccountEntityStub.setBalance(AMOUNT);
        clientAccountEntityStub.setCreatedAt(Timestamp.from(Instant.now()));
        clientAccountEntityStub.setCurrency(CurrencyCode.RUB.name());
        clientAccountEntityStub.setClientByClientId(clientEntityStub);

    }

    @Test
    @Order(1)
    void setBalanceEntity() {

        Integer actualId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(CLIENT_ID, actualId);

        Integer actualAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        Assertions.assertEquals(CLIENT_ACCOUNT_ID, actualAccountId);

        ClientAccountEntity clientEntityAccountById = clientAccountService.findClientEntityAccountById(1);

        Integer actualTransactionId = transactionService.setTransactionEntity(
                transactionEntity,
                TransactionType.REFILL,
                AMOUNT, CurrencyCode.RUB,
                null, ACCOUNT_NO);
        Assertions.assertEquals(1, actualTransactionId);

        Integer balanceId = balanceService.setBalanceEntity(
                clientEntityAccountById,
                balanceEntity,
                transactionEntity, AMOUNT, NEW_AMOUNT);
        Assertions.assertEquals(BALANCE_ID, balanceId);

        Assertions.assertEquals(AMOUNT, balanceEntity.getBeforeBalance());
        Assertions.assertEquals(NEW_AMOUNT, balanceEntity.getPastBalance());

    }
}