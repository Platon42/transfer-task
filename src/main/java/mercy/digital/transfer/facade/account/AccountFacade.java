package mercy.digital.transfer.facade.account;

import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;

public interface AccountFacade {
    ResponseModel addClientAccount(AddClientAccount clientAccount);
}
