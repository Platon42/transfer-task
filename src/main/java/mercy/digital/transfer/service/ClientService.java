package mercy.digital.transfer.service;

import mercy.digital.transfer.domain.ClientEntity;

import java.util.List;

public interface ClientService {
    void addEntityClient(ClientEntity clientEntity);

    ClientEntity findEntityAccountById(Integer id);

    List<ClientEntity> findAllEntityAccounts();
}
