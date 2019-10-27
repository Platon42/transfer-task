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
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;

import java.time.LocalDateTime;

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
        Integer id = beneficiaryService.addEntityBeneficiary(beneficiaryEntity);
        responseModel.setService("addBeneficiary");
        responseModel.setDateTime(LocalDateTime.now());

        if (id != null) {
            responseModel.setMessage("Success beneficiary added");
            responseModel.setAdditional(String.valueOf(id));
            responseModel.setStatus(0);
        } else {
            responseModel.setMessage("Common error occurred, see log for details");
            responseModel.setStatus(-1);
        }
        return responseModel;
    }

    public ResponseModel addBeneficiaryAccount(AddBeneficiaryAccount addBeneficiaryAccount) {

        int beneficiaryId = addBeneficiaryAccount.getGetBeneficiary().getBeneficiaryId();
        BeneficiaryEntity entityBeneficiaryById = beneficiaryService.findEntityBeneficiaryById(beneficiaryId);

        responseModel.setDateTime(LocalDateTime.now());
        responseModel.setService("addBeneficiaryAccount");

        if (entityBeneficiaryById != null) {
            BeneficiaryAccountEntity beneficiaryAccountEntity = this.mapper.map(addBeneficiaryAccount,
                    BeneficiaryAccountEntity.class);
            beneficiaryAccountEntity.setBeneficiaryByBeneficiaryId(entityBeneficiaryById);
            Integer integer = beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntity);
            if (integer == null) {
                responseModel.setMessage("Cannot create Beneficiary account, with Beneficiary Id " +
                        beneficiaryId + " see log for details");
                responseModel.setStatus(-1);
            } else {
                responseModel.setAdditional(String.valueOf(beneficiaryId));
                responseModel.setMessage("Success beneficiary account added");
                responseModel.setStatus(0);
            }
        } else {
            responseModel.setStatus(-1);
            responseModel.setMessage("Cannot create Beneficiary account, with Beneficiary Id " +
                    beneficiaryId + " see log for details");
        }
        return responseModel;
    }

}
