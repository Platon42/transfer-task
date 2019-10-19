package mercy.digital.transfer.dao.balance;
import com.j256.ormlite.dao.Dao;
import mercy.digital.transfer.domain.BalanceEntity;

public interface BalanceDao {

    Dao<BalanceEntity, Integer> getBalanceDao();

}
