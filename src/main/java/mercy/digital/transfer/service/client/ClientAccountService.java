package mercy.digital.transfer.service.client;

import mercy.digital.transfer.domain.ClientAccountEntity;
import java.util.List;

public interface ClientAccountService {
    void addEntityAccount(ClientAccountEntity accountEntity);
    ClientAccountEntity findEntityAccountById(Integer id);
    List<ClientAccountEntity> findAllEntityAccounts();
}
