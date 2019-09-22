package mercy.digital.transfer.dao.client;

import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.ClientEntity;

public interface ClientDao {
    Dao<ClientEntity, Integer> getClientDao();
}
