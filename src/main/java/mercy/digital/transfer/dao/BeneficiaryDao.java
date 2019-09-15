package mercy.digital.transfer.dao;

import com.j256.ormlite.dao.Dao;

import java.util.HashMap;

public interface BeneficiaryDao {
    HashMap<String, Dao<?, ?>> getBeneficiaryDao();
}
