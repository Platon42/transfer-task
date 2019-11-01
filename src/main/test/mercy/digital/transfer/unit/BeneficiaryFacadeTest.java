package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.BeneficiaryEntity;
import mercy.digital.transfer.facade.beneficiary.BeneficiaryFacade;
import mercy.digital.transfer.facade.beneficiary.BeneficiaryFacadeImpl;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.presentation.beneficiary.AddBeneficiary;
import mercy.digital.transfer.presentation.beneficiary.GetBeneficiary;
import mercy.digital.transfer.presentation.beneficiary.account.AddBeneficiaryAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
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
@IncludeModule(BeneficiaryFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BeneficiaryFacadeTest {

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;

    @Inject
    private BeneficiaryService beneficiaryService;

    private AddBeneficiary addBeneficiaryStub;
    private AddBeneficiaryAccount addBeneficiaryAccountStub;

    private BeneficiaryFacade beneficiaryFacade;

    @BeforeAll
    void setUp() {

        addBeneficiaryStub = new AddBeneficiary();

        GetBeneficiary getBeneficiary = new GetBeneficiary();
        getBeneficiary.setBeneficiaryId(1);

        addBeneficiaryAccountStub = new AddBeneficiaryAccount();
        addBeneficiaryAccountStub.setGetBeneficiary(getBeneficiary);

        beneficiaryService = Mockito.mock(BeneficiaryService.class);
        beneficiaryAccountService = Mockito.mock(BeneficiaryAccountService.class);
        beneficiaryFacade = new BeneficiaryFacadeImpl(beneficiaryAccountService, beneficiaryService);
    }

    @Test
    @Order(1)
    void addBeneficiarySuccess() {

        when(beneficiaryService.addEntityBeneficiary(any())).thenReturn(1);
        beneficiaryFacade.addBeneficiary(addBeneficiaryStub);

        ResponseModel actualResponseModel = beneficiaryFacade.addBeneficiary(addBeneficiaryStub);
        ResponseModel expectedResponseModel = new ResponseModel();
        expectedResponseModel.setStatus(0);

        Assertions.assertEquals(actualResponseModel.getStatus(), expectedResponseModel.getStatus());

    }

    @Test
    @Order(2)
    void addBeneficiaryFail() {

        when(beneficiaryService.addEntityBeneficiary(any())).thenReturn(null);
        beneficiaryFacade.addBeneficiary(addBeneficiaryStub);

        ResponseModel actualResponseModel = beneficiaryFacade.addBeneficiary(addBeneficiaryStub);
        ResponseModel expectedResponseModel = new ResponseModel();
        expectedResponseModel.setStatus(-1);

        Assertions.assertEquals(actualResponseModel.getStatus(), expectedResponseModel.getStatus());

    }

    @Test
    @Order(3)
    void addBeneficiaryAccount() {

        when(beneficiaryService.findEntityBeneficiaryById(any())).thenReturn(new BeneficiaryEntity());
        when(beneficiaryAccountService.addBeneficiaryEntityAccount(any())).thenReturn(1);

        beneficiaryFacade.addBeneficiaryAccount(addBeneficiaryAccountStub);

        ResponseModel expectedModel = new ResponseModel();
        expectedModel.setStatus(0);
        ResponseModel actualModel = beneficiaryFacade.addBeneficiaryAccount(addBeneficiaryAccountStub);

        Assertions.assertEquals(expectedModel.getStatus(), actualModel.getStatus());

    }

    @Test
    @Order(3)
    void addBeneficiaryAccountNotFoundBeneficiary() {

        when(beneficiaryService.findEntityBeneficiaryById(any())).thenReturn(null);
        when(beneficiaryAccountService.addBeneficiaryEntityAccount(any())).thenReturn(1);

        beneficiaryFacade.addBeneficiaryAccount(addBeneficiaryAccountStub);

        ResponseModel expectedModel = new ResponseModel();
        expectedModel.setStatus(-1);
        ResponseModel actualModel = beneficiaryFacade.addBeneficiaryAccount(addBeneficiaryAccountStub);

        Assertions.assertEquals(expectedModel.getStatus(), actualModel.getStatus());

    }

    @Test
    @Order(4)
    void addBeneficiaryAccountCannotAddEntity() {

        when(beneficiaryService.findEntityBeneficiaryById(any())).thenReturn(new BeneficiaryEntity());
        when(beneficiaryAccountService.addBeneficiaryEntityAccount(any())).thenReturn(null);

        beneficiaryFacade.addBeneficiaryAccount(addBeneficiaryAccountStub);

        ResponseModel expectedModel = new ResponseModel();
        expectedModel.setStatus(-1);
        ResponseModel actualModel = beneficiaryFacade.addBeneficiaryAccount(addBeneficiaryAccountStub);

        Assertions.assertEquals(expectedModel.getStatus(), actualModel.getStatus());

    }
}