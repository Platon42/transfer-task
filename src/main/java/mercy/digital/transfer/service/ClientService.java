package mercy.digital.transfer.service;

import mercy.digital.transfer.domain.ClientEntity;

import java.util.List;

public interface ClientService {
    void addClient(ClientEntity clientEntity);
    ClientEntity findAccountById(Integer id);
    List<ClientEntity> findAllAccounts();
}
