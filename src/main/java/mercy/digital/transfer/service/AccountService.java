package mercy.digital.transfer.service;

import mercy.digital.transfer.domain.AccountEntity;

import java.util.List;

public interface AccountService {
    void addEntityAccount(AccountEntity accountEntity);

    AccountEntity findEntityAccountById(Integer id);

    List<AccountEntity> findAllEntityAccounts();
}
