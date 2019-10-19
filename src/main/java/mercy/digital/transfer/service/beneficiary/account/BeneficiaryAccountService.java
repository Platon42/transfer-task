package mercy.digital.transfer.service.beneficiary.account;

import mercy.digital.transfer.domain.BeneficiaryAccountEntity;

import java.util.List;

public interface BeneficiaryAccountService {
    void addBeneficiaryEntityAccount(BeneficiaryAccountEntity accountEntity);
    BeneficiaryAccountEntity findBeneficiaryEntityAccountById(Integer id);
    List<BeneficiaryAccountEntity> findAllEntityBeneficiaryAccounts();
}
