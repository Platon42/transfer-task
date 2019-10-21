package mercy.digital.transfer.service.client.account;

import mercy.digital.transfer.domain.ClientAccountEntity;

import java.util.List;

public interface ClientAccountService {

    void addClientEntityAccount(ClientAccountEntity accountEntity);
    ClientAccountEntity findClientEntityAccountById(Integer id);
    ClientAccountEntity findClientEntityAccountByAccountNo(Integer accountNo);
    List<ClientAccountEntity> findAllEntityClientAccounts();
    void updateColumnClientAccount(Integer clientId, String columnName, String value);
    void updateClientAccount(ClientAccountEntity accountEntity);
}
