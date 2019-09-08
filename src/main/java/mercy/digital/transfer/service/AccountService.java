package mercy.digital.transfer.service;

import mercy.digital.transfer.domain.AccountEntity;

import java.util.List;

public interface AccountService {
    void addAccount (AccountEntity accountEntity);
    AccountEntity findAccountById (Integer id);
    List<AccountEntity> findAllAccounts ();
}
