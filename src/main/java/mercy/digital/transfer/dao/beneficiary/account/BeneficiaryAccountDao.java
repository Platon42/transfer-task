package mercy.digital.transfer.dao.beneficiary.account;

import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;

public interface BeneficiaryAccountDao {
    Dao<BeneficiaryAccountEntity, Integer> getBeneficiaryAccountDao();
}
