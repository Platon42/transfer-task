package mercy.digital.transfer.facade.beneficiary;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.BeneficiaryEntity;
import mercy.digital.transfer.presentation.beneficiary.AddBeneficiary;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;

public class BeneficiaryFacadeImpl implements BeneficiaryFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();

    @Inject
    private BeneficiaryService beneficiaryService;

    public void addBeneficiary(AddBeneficiary beneficiary) {
        BeneficiaryEntity beneficiaryEntity = this.mapper.map(beneficiary, BeneficiaryEntity.class);
        beneficiaryService.addEntityBeneficiary(beneficiaryEntity);
    }

}
