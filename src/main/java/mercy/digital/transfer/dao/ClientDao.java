package mercy.digital.transfer.dao;

import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.ClientEntity;

import java.util.List;

public interface ClientDao {
    Dao<ClientEntity, Integer> getClientDao();
}
