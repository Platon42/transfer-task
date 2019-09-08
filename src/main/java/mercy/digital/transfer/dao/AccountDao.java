package mercy.digital.transfer.dao;

import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.AccountEntity;

public interface AccountDao {
    Dao<AccountEntity,Integer> getAccountDao ();
}
