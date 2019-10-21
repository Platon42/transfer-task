package mercy.digital.transfer.service.beneficiary;

import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.util.List;

public interface BeneficiaryService {
    Integer addEntityBeneficiary(BeneficiaryEntity beneficiaryEntity);
    BeneficiaryEntity findEntityBeneficiaryById(Integer id);
    List<BeneficiaryEntity> findAllEntityBeneficiary();
}
