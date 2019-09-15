package mercy.digital.transfer.service;

import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.util.List;

public interface BeneficiaryService {
    void addEntityBeneficiary(AccountEntity accountEntity, BeneficiaryEntity beneficiaryEntity);
    BeneficiaryEntity findEntityBeneficiaryById(Integer id);
    List<BeneficiaryEntity> findAllEntityBeneficiary();
}
