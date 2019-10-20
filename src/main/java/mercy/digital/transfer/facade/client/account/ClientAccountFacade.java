package mercy.digital.transfer.facade.client.account;

import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transaction.refill.GetRefill;
import mercy.digital.transfer.presentation.transaction.transfer.GetTransfer;

public interface ClientAccountFacade {
    ResponseModel addClientAccount(AddClientAccount clientAccount);

    ResponseModel doTransfer(GetTransfer transfer);

    ResponseModel doRefill(GetRefill refill);
}
