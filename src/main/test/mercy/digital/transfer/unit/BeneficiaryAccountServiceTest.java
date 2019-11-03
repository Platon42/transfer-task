package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
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
@IncludeModule(BeneficiaryFacadeModule.class)
@IncludeModule(AccountFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeneficiaryAccountServiceTest {

    private static final Integer CLIENT_ID = 1;
    private static final Integer CLIENT_ACCOUNT_ID = 1;
    private static final Integer BENEFICIARY_ACCOUNT_ID = 1;

    private static final Integer CLIENT_ID_2 = 2;
    private static final Integer CLIENT_ACCOUNT_ID_2 = 2;
    private static final Integer BENEFICIARY_ACCOUNT_ID_2 = 2;

    private static final Integer CLIENT_ID_3 = 3;
    private static final Integer CLIENT_ACCOUNT_ID_3 = 3;
    private static final Integer BENEFICIARY_ACCOUNT_ID_3 = 3;

    private static final Integer CLIENT_NO = 1012;
    private static final Integer CLIENT_NO_2 = 1013;
    private static final Integer CLIENT_NO_3 = 1014;

    private static final Integer BENEFICIARY_ACCOUNT_NO_CLIENT = 1012;
    private static final Integer BENEFICIARY_ACCOUNT_NO_CLIENT_2 = 1014;

    private static final Integer BENEFICIARY_ACCOUNT_NO_NON_CLIENT = 1023;


    private static final CurrencyCode BENEFICIARY_ACCOUNT_CURRENCY = CurrencyCode.RUB;
    private static final CurrencyCode BENEFICIARY_ACCOUNT_CURRENCY_DIFF = CurrencyCode.EUR;

    private static final CurrencyCode CLIENT_ACCOUNT_CURRENCY = CurrencyCode.RUB;


    private static final String FIRST_NAME = "Ivan";
    private static BeneficiaryAccountEntity beneficiaryAccountEntityStub = new BeneficiaryAccountEntity();
    private static ClientAccountEntity clientAccountEntityStub = new ClientAccountEntity();
    private static ClientEntity clientEntityStub = new ClientEntity();


    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;

    @Inject
    private ClientAccountService clientAccountService;
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
    void addBeneficiaryAsClient() {

        Integer actualClientId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(CLIENT_ID, actualClientId);

        clientAccountEntityStub.setAccountNo(CLIENT_NO);
        clientAccountEntityStub.setClientByClientId(clientEntityStub);
        clientAccountEntityStub.setCurrency(CLIENT_ACCOUNT_CURRENCY.name());

        beneficiaryAccountEntityStub.setAccountNo(BENEFICIARY_ACCOUNT_NO_CLIENT);
        beneficiaryAccountEntityStub.setCurrency(BENEFICIARY_ACCOUNT_CURRENCY.name());

        Integer actualClientAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        Assertions.assertEquals(CLIENT_ACCOUNT_ID, actualClientAccountId);

        Integer actualBeneficiaryId = beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntityStub);
        Assertions.assertEquals(BENEFICIARY_ACCOUNT_ID, actualBeneficiaryId);

        BeneficiaryAccountEntity beneficiaryEntityAccountById = beneficiaryAccountService.findBeneficiaryEntityAccountById(BENEFICIARY_ACCOUNT_ID);
        Assertions.assertEquals(true, beneficiaryEntityAccountById.getClient());

    }
    @Test
    @Order(2)
    void addBeneficiaryNonClient() {

        Integer actualClientId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(CLIENT_ID_2, actualClientId);

        beneficiaryAccountEntityStub.setAccountNo(BENEFICIARY_ACCOUNT_NO_NON_CLIENT);
        beneficiaryAccountEntityStub.setCurrency(BENEFICIARY_ACCOUNT_CURRENCY.name());

        clientAccountEntityStub.setAccountNo(CLIENT_NO_2);
        clientAccountEntityStub.setClientByClientId(clientEntityStub);
        clientAccountEntityStub.setCurrency(CLIENT_ACCOUNT_CURRENCY.name());

        Integer actualClientAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        Assertions.assertEquals(CLIENT_ACCOUNT_ID_2, actualClientAccountId);

        Integer actualBeneficiaryId = beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntityStub);
        Assertions.assertEquals(BENEFICIARY_ACCOUNT_ID_2, actualBeneficiaryId);

        BeneficiaryAccountEntity beneficiaryEntityAccountById = beneficiaryAccountService.findBeneficiaryEntityAccountById(BENEFICIARY_ACCOUNT_ID_2);
        Assertions.assertEquals(false, beneficiaryEntityAccountById.getClient());
    }

    @Test
    @Order(3)
    void checkAndFixSameBeneficiaryAndClientCurrency() {

        Integer actualClientId = clientService.addEntityClient(clientEntityStub);
        Assertions.assertEquals(CLIENT_ID_3, actualClientId);

        clientAccountEntityStub.setAccountNo(CLIENT_NO_3);
        clientAccountEntityStub.setCurrency(CLIENT_ACCOUNT_CURRENCY.name());
        clientAccountEntityStub.setClientByClientId(clientEntityStub);

        beneficiaryAccountEntityStub.setAccountNo(BENEFICIARY_ACCOUNT_NO_CLIENT_2);
        beneficiaryAccountEntityStub.setCurrency(BENEFICIARY_ACCOUNT_CURRENCY_DIFF.name());

        Integer actualClientAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
        Assertions.assertEquals(CLIENT_ACCOUNT_ID_3, actualClientAccountId);

        Integer actualBeneficiaryId = beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntityStub);
        Assertions.assertEquals(BENEFICIARY_ACCOUNT_ID_3, actualBeneficiaryId);

        BeneficiaryAccountEntity beneficiaryEntityAccountById = beneficiaryAccountService.findBeneficiaryEntityAccountById(BENEFICIARY_ACCOUNT_ID_3);
        Assertions.assertEquals(true, beneficiaryEntityAccountById.getClient());
        Assertions.assertEquals(CLIENT_ACCOUNT_CURRENCY.name(), beneficiaryEntityAccountById.getCurrency());
    }

    @Test
    @Order(4)
    void findBeneficiaryEntityAccountById() {
        BeneficiaryAccountEntity beneficiaryEntityAccountById = beneficiaryAccountService.findBeneficiaryEntityAccountById(BENEFICIARY_ACCOUNT_ID_3);
        Assertions.assertNotNull(beneficiaryEntityAccountById);
        Assertions.assertEquals(BENEFICIARY_ACCOUNT_ID_3, beneficiaryEntityAccountById.getBeneficiaryAccountId());
    }

    @Test
    @Order(5)
    void findBeneficiaryEntityAccountByAccountNo() {
        BeneficiaryAccountEntity beneficiaryEntityAccountById = beneficiaryAccountService.findBeneficiaryEntityAccountByAccountNo(BENEFICIARY_ACCOUNT_NO_CLIENT_2);
        Assertions.assertNotNull(beneficiaryEntityAccountById);
        Assertions.assertEquals(BENEFICIARY_ACCOUNT_NO_CLIENT_2, beneficiaryEntityAccountById.getAccountNo());
    }

    @Test
    @Order(6)
    void findAllEntityBeneficiaryAccounts() {
        beneficiaryAccountEntityStub = new BeneficiaryAccountEntity();

        beneficiaryAccountEntityStub.setAccountNo(102233);
        beneficiaryAccountEntityStub.setCurrency(CurrencyCode.USD.name());

        Integer actualBeneficiaryId = beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntityStub);
        Assertions.assertEquals(4, actualBeneficiaryId);
        List<BeneficiaryAccountEntity> allEntityBeneficiaryAccounts = beneficiaryAccountService.findAllEntityBeneficiaryAccounts();
        Assertions.assertEquals(CurrencyCode.USD.name(), allEntityBeneficiaryAccounts.get(3).getCurrency());
    }
}