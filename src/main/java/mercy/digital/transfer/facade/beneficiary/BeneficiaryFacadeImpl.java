package mercy.digital.transfer.facade.beneficiary;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.beneficiary.AddBeneficiary;
import mercy.digital.transfer.presentation.beneficiary.account.AddBeneficiaryAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;

import java.time.ZonedDateTime;

public class BeneficiaryFacadeImpl implements BeneficiaryFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

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
            //beneficiaryService.addEntityBeneficiary(beneficiaryAccountEntity);
            responseModel.setMessage("Success");
        } else {
            //responseModel.setErrorMessage("Cannot create client account, with Client Id " +
              //      clientId + " see log for details");
        }
        return responseModel;
    }

}
