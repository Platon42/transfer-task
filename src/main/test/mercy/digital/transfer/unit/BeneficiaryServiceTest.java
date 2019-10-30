package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.BeneficiaryEntity;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(BeneficiaryFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeneficiaryServiceTest {

    private static final Integer BENEFICIARY_ID = 1;
    private static final String CITY = "Moscow";
    private static final String SECOND_CITY = "London";
    private static BeneficiaryEntity beneficiaryEntityStub = new BeneficiaryEntity();

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    BeneficiaryService beneficiaryService;

    @BeforeAll
    static void setUp() {
        beneficiaryEntityStub.setCity(CITY);
        beneficiaryEntityStub.setCountry("RU");
    }

    @Order(1)
    @Test
    void addEntityBeneficiary() {
        Integer actualBeneficiaryId = beneficiaryService.addEntityBeneficiary(beneficiaryEntityStub);
        Assertions.assertEquals(BENEFICIARY_ID, actualBeneficiaryId);
    }

    @Order(2)
    @Test
    void findEntityBeneficiaryById() {
        BeneficiaryEntity actualBeneficiaryById = beneficiaryService.findEntityBeneficiaryById(BENEFICIARY_ID);
        String actualCity = actualBeneficiaryById.getCity();
        Assertions.assertEquals(CITY, actualCity);
    }

    @Order(3)
    @Test
    void findAllEntityBeneficiary() {
        beneficiaryEntityStub = new BeneficiaryEntity();
        beneficiaryEntityStub.setCity(SECOND_CITY);
        beneficiaryService.addEntityBeneficiary(beneficiaryEntityStub);

        List<BeneficiaryEntity> allEntityBeneficiary = beneficiaryService.findAllEntityBeneficiary();
        Assertions.assertEquals(SECOND_CITY, allEntityBeneficiary.get(1).getCity());
    }
}