package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(BeneficiaryFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeneficiaryAccountServiceTest {

    private static final Integer CLIENT_ID = 1;
    private static final Integer ACCOUNT_NO = 1111;
    private static final Integer OUR_CLIENT = 1012;

    private static final String FIRST_NAME = "Ivan";
    private static BeneficiaryAccountEntity beneficiaryAccountEntityStub = new BeneficiaryAccountEntity();

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;

    @BeforeEach
    void setUp() {
        beneficiaryAccountEntityStub.setCurrency(CurrencyCode.EUR.name());
        beneficiaryAccountEntityStub.setAccountNo(ACCOUNT_NO);
    }

    @Test
    void addBeneficiaryEntityAccount() {
        beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntityStub);
    }

    @Test
    void findBeneficiaryEntityAccountById() {
    }

    @Test
    void findBeneficiaryEntityAccountByAccountNo() {
    }

    @Test
    void findAllEntityBeneficiaryAccounts() {
    }
}