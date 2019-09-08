package mercy.digital.transfer.dao;

import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.util.List;

public interface BeneficiaryDao {
    void addBeneficiary (AccountEntity accountEntity, BeneficiaryEntity beneficiaryEntity);
    BeneficiaryEntity findBeneficiaryById (Integer id);
    List<BeneficiaryEntity> findAllBeneficiary ();
}
