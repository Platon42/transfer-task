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

import java.time.ZonedDateTime;

public class BeneficiaryFacadeImpl implements BeneficiaryFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;
    @Inject
    private BeneficiaryService beneficiaryService;

    public void addBeneficiary(AddBeneficiary beneficiary) {
        BeneficiaryEntity beneficiaryEntity = this.mapper.map(beneficiary, BeneficiaryEntity.class);
        beneficiaryService.addEntityBeneficiary(beneficiaryEntity);
    }

    public ResponseModel addBeneficiaryAccount(AddBeneficiaryAccount beneficiaryAccount) {

        int beneficiaryId = beneficiaryAccount.getGetBeneficiary().getBeneficiaryId();
        BeneficiaryEntity entityBeneficiaryById = beneficiaryService.findEntityBeneficiaryById(beneficiaryId);

        responseModel.setDateTime(ZonedDateTime.now());
        responseModel.setService(this.getClass().getName());

        if (entityBeneficiaryById != null) {
            BeneficiaryAccountEntity beneficiaryAccountEntity = this.mapper.map(beneficiaryAccount, BeneficiaryAccountEntity.class);
            beneficiaryAccountEntity.setBeneficiaryByBeneficiaryId(entityBeneficiaryById);
            beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntity);
            responseModel.setMessage("Success");
        } else {
            responseModel.setErrorMessage("Cannot create Beneficiary account, with Beneficiary Id " +
                    beneficiaryId + " see log for details");
        }
        return responseModel;
    }

}
