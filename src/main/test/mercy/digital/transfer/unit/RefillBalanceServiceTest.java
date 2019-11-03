package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceService;
import mercy.digital.transfer.utils.db.H2Utils;
import mercy.digital.transfer.utils.prop.Environment;
import mercy.digital.transfer.utils.prop.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.apache.commons.lang3.Range;
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
class RefillBalanceServiceTest {

    private static ClientEntity clientEntityStub = new ClientEntity();
    private static ClientAccountEntity clientAccountEntityStub = new ClientAccountEntity();

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }


    @Inject
    private RefillBalanceService refillBalanceService;

    @Inject
    private ClientService clientService;

    @Inject
    private ClientAccountService clientAccountService;


    @BeforeAll
    static void setClientAndAccount() {

        clientEntityStub.setFirstName("Ivan");
        clientEntityStub.setLastName("Smirnov");
        clientEntityStub.setResidentCountry("RU");
        clientEntityStub.setSex(1);
        clientEntityStub.setResidentCountry("Russia");
        clientEntityStub.setMiddleName("Vlad");
        clientEntityStub.setBirthday(new Date(19082019));

        clientAccountEntityStub.setCreatedAt(Timestamp.from(Instant.now()));
        clientAccountEntityStub.setCurrency(CurrencyCode.RUB.name());

    }

    @Test
    @Order(1)
    void refillBalanceDiffCurrencyService() {

        Integer ACCOUNT_NO = 10122;
        Double AMOUNT = 100.00;

        clientAccountEntityStub.setAccountNo(ACCOUNT_NO);
        clientAccountEntityStub.setBalance(AMOUNT);
        clientAccountEntityStub.setClientByClientId(clientEntityStub);

        Integer actualId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(1, actualId);

        Integer actualAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        Assertions.assertEquals(1, actualAccountId);

        TransactionStatus transactionStatus = refillBalanceService.refillBalance(ACCOUNT_NO, 200.0, CurrencyCode.USD);
        Assertions.assertEquals(TransactionStatus.REFILL_COMPLETED, transactionStatus);
        Double newBalance = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO).getBalance();
        Range<Double> newBalanceRange = Range.between(12700.00, 12900.00);
        Assertions.assertTrue(newBalanceRange.contains(newBalance));
    }

    @Test
    @Order(2)
    void refillBalanceSameCurrencyService() {

        Integer ACCOUNT_NO = 10123;
        Double AMOUNT = 100.00;

        clientAccountEntityStub.setAccountNo(ACCOUNT_NO);
        clientAccountEntityStub.setBalance(AMOUNT);
        clientAccountEntityStub.setClientByClientId(clientEntityStub);

        clientService.addEntityClient(clientEntityStub);

        clientAccountService.addClientEntityAccount(clientAccountEntityStub);

        TransactionStatus transactionStatus = refillBalanceService.refillBalance(ACCOUNT_NO, 200.0, CurrencyCode.RUB);
        Assertions.assertEquals(TransactionStatus.REFILL_COMPLETED, transactionStatus);
        Double newBalance = clientAccountService.findClientEntityAccountByAccountNo(ACCOUNT_NO).getBalance();
        Assertions.assertEquals(300.00, newBalance);
    }

    @Test
    @Order(3)
    void refillBalanceServiceException() {

        Integer ACCOUNT_NO = 10124;
        Double AMOUNT = 100.00;

        clientAccountEntityStub.setAccountNo(ACCOUNT_NO);
        clientAccountEntityStub.setBalance(AMOUNT);
        clientAccountEntityStub.setClientByClientId(clientEntityStub);

        clientService.addEntityClient(clientEntityStub);

        clientAccountService.addClientEntityAccount(clientAccountEntityStub);

        TransactionStatus transactionStatus = refillBalanceService.refillBalance(ACCOUNT_NO, -200.0, CurrencyCode.USD);
        Assertions.assertEquals(TransactionStatus.INCORRECT_AMOUNT, transactionStatus);

    }
}