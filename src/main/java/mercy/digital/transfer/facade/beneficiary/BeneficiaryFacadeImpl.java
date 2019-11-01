package mercy.digital.transfer.facade.beneficiary;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;
import mercy.digital.transfer.presentation.beneficiary.AddBeneficiary;
import mercy.digital.transfer.presentation.beneficiary.account.AddBeneficiaryAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.response.ResponseServiceBuilder;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;

public class BeneficiaryFacadeImpl implements BeneficiaryFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

    private final BeneficiaryAccountService beneficiaryAccountService;
    private final BeneficiaryService beneficiaryService;

    @Inject
    public BeneficiaryFacadeImpl(BeneficiaryAccountService beneficiaryAccountService,
                                 BeneficiaryService beneficiaryService) {
        this.beneficiaryAccountService = beneficiaryAccountService;
        this.beneficiaryService = beneficiaryService;
    }

    public ResponseModel addBeneficiary(AddBeneficiary beneficiary) {
        BeneficiaryEntity beneficiaryEntity = this.mapper.map(beneficiary, BeneficiaryEntity.class);
        Integer beneficiaryId = beneficiaryService.addEntityBeneficiary(beneficiaryEntity);

        ResponseServiceBuilder responseServiceBuilder =
                new ResponseServiceBuilder(responseModel, "addBeneficiary");

        if (beneficiaryId != null) {
            responseServiceBuilder.withMessage("Success beneficiary added");
            responseServiceBuilder.withStatus(0);
            responseServiceBuilder.withAdditional("beneficiaryId:" + beneficiaryId);
        } else {
            responseServiceBuilder.withMessage("Common error occurred, see log for details");
            responseServiceBuilder.withStatus(-1);
        }
        responseServiceBuilder.build();
        return responseModel;
    }

    public ResponseModel addBeneficiaryAccount(AddBeneficiaryAccount addBeneficiaryAccount) {

        int beneficiaryId = addBeneficiaryAccount.getGetBeneficiary().getBeneficiaryId();
        BeneficiaryEntity entityBeneficiaryById = beneficiaryService.findEntityBeneficiaryById(beneficiaryId);

        ResponseServiceBuilder responseServiceBuilder =
                new ResponseServiceBuilder(responseModel, "addBeneficiaryAccount");

        if (entityBeneficiaryById != null) {
            BeneficiaryAccountEntity beneficiaryAccountEntity = this.mapper.map(addBeneficiaryAccount,
                    BeneficiaryAccountEntity.class);
            beneficiaryAccountEntity.setBeneficiaryByBeneficiaryId(entityBeneficiaryById);
            Integer beneficiaryAccountId = beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntity);

            if (beneficiaryAccountId != null) {
                responseServiceBuilder.withMessage("Success beneficiary account added");
                responseServiceBuilder.withStatus(0);
                responseServiceBuilder.withAdditional("beneficiaryAccountId:" + beneficiaryAccountId);
            } else {
                responseServiceBuilder.withMessage("Cannot create Beneficiary account, with Beneficiary Id " +
                        beneficiaryId + " see log for details");
                responseServiceBuilder.withStatus(-1);
            }
        } else {
            responseServiceBuilder.withMessage("Cannot create Beneficiary account, with Beneficiary Id " +
                    beneficiaryId + " see log for details");
            responseServiceBuilder.withStatus(-1);
        }
        responseServiceBuilder.build();
        return responseModel;
    }

}
