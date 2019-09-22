package mercy.digital.transfer.dao.beneficiary;

import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.BeneficiaryEntity;

public interface BeneficiaryDao {
    Dao<BeneficiaryEntity,Integer> getBeneficiaryDao ();
}
