package mercy.digital.transfer.facade.account;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.presentation.client.GetClient;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transfer.GetTransfer;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.CurrencyCode;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.TransactionStatus;
import mercy.digital.transfer.service.transaction.TransactionType;
import mercy.digital.transfer.service.transfer.TransferService;

import java.time.ZonedDateTime;

public class ClientAccountFacadeImpl implements ClientAccountFacade {

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();
    private ResponseModel responseModel = new ResponseModel();

    @Inject
    private TransactionService transactionService;

    @Inject
    private BalanceService balanceService;

    @Inject
    private TransferService transferService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private ClientService clientService;

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
        TransactionStatus transactionStatus = transactionService.doTransfer(
                transfer.getAccountNoSender(),
                transfer.getAccountNoReceiver(),
                transfer.getAmount(),
                TransactionType.FUNDS_TRANSFER,
                CurrencyCode.valueOf(transfer.getChangeCurrency())
        );
        responseModel.setMessage(transactionStatus.name());
        return responseModel;
    }

}