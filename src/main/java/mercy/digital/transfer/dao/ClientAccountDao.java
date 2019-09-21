package mercy.digital.transfer.dao;

import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.ClientAccountEntity;

public interface ClientAccountDao {
    Dao<ClientAccountEntity,Integer> getAccountDao ();
}
