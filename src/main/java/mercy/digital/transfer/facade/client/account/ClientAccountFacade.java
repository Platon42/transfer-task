package mercy.digital.transfer.facade.client.account;

import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transaction.GetTransactionDetails;
import mercy.digital.transfer.presentation.transaction.refill.DoRefill;
import mercy.digital.transfer.presentation.transaction.transfer.DoTransfer;

public interface ClientAccountFacade {
    ResponseModel addClientAccount(AddClientAccount clientAccount);
    ResponseModel doTransfer(DoTransfer transfer);
    ResponseModel doRefill(DoRefill refill);
    GetTransactionDetails getTransactionDetails (Integer accountId);
}
