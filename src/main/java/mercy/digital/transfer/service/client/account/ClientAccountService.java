package mercy.digital.transfer.service.client.account;

import mercy.digital.transfer.domain.ClientAccountEntity;

import java.util.List;

public interface ClientAccountService {
    void addClientEntityAccount(ClientAccountEntity accountEntity);

    ClientAccountEntity findClientEntityAccountById(Integer id);

    List<ClientAccountEntity> findAllEntityClientAccounts();
}
