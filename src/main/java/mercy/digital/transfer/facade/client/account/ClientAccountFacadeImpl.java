package mercy.digital.transfer.facade.client.account;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transaction.refill.GetRefill;
import mercy.digital.transfer.presentation.transaction.transfer.GetTransfer;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceService;
import mercy.digital.transfer.service.transaction.transfer.TransferService;

import java.time.ZonedDateTime;

public class ClientAccountFacadeImpl implements ClientAccountFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

    @Inject
    private TransferService transferService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private ClientService clientService;

    @Inject
    private RefillBalanceService refillBalanceService;

    public ResponseModel addClientAccount(AddClientAccount clientAccount) {

        int clientId = clientAccount.getGetClient().getClientId();
        ClientEntity accountById = clientService.findEntityAccountById(clientId);

        responseModel.setDateTime(ZonedDateTime.now());
        responseModel.setService(this.getClass().getName());

        if (accountById != null) {
            ClientAccountEntity accountEntity = this.mapper.map(clientAccount, ClientAccountEntity.class);
            accountEntity.setClientByClientId(accountById);
            clientAccountService.addClientEntityAccount(accountEntity);
            responseModel.setMessage("Success");
        } else {
            responseModel.setErrorMessage("Cannot create client account, with Client Id " +
                    clientId + " see log for details");
        }
        return responseModel;
    }

    public ResponseModel doTransfer (GetTransfer transfer) {
        TransactionStatus transactionStatus = transferService.doTransfer(
                transfer.getAccountNoSender(),
                transfer.getAccountNoReceiver(),
                transfer.getAmount(),
                TransactionType.FUNDS_TRANSFER,
                CurrencyCode.valueOf(transfer.getChangeCurrency())
        );
        responseModel.setMessage(transactionStatus.name());
        return responseModel;
    }

    public ResponseModel doRefill(GetRefill refill) {
        refillBalanceService.refillBalance(
                refill.getAccountNo(),
                refill.getAmount(),
                CurrencyCode.valueOf(refill.getCurrency()));
        responseModel.setMessage("");
        return responseModel;
    }

}
