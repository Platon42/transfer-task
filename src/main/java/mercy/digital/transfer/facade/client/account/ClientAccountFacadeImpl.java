package mercy.digital.transfer.facade.client.account;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transaction.GetTransactionDetails;
import mercy.digital.transfer.presentation.transaction.refill.DoRefill;
import mercy.digital.transfer.presentation.transaction.transfer.DoTransfer;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceService;
import mercy.digital.transfer.service.transaction.transfer.TransferService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

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

    @Inject
    private TransactionService transactionService;

    public ResponseModel addClientAccount(AddClientAccount clientAccount) {

        int clientId = clientAccount.getGetClient().getClientId();
        ClientEntity accountById = clientService.findEntityAccountById(clientId);

        responseModel.setDateTime(LocalDateTime.now());
        responseModel.setService("addClientAccount");

        if (accountById != null) {
            ClientAccountEntity accountEntity = this.mapper.map(clientAccount, ClientAccountEntity.class);
            accountEntity.setClientByClientId(accountById);
            clientAccountService.addClientEntityAccount(accountEntity);
            responseModel.setMessage("Success created client Account");
        } else {
            responseModel.setErrorMessage("Cannot create client account, with Client Id " +
                    clientId + " see log for details");
        }
        return responseModel;
    }

    public ResponseModel doTransfer (DoTransfer transfer) {
        TransactionStatus transactionStatus = transferService.doTransfer (
                transfer.getAccountNoSender(),
                transfer.getAccountNoReceiver(),
                transfer.getAmount(),
                TransactionType.FUNDS_TRANSFER,
                CurrencyCode.valueOf(transfer.getChangeCurrency())
        );
        responseModel.setMessage(transactionStatus.name());
        return responseModel;
    }

    public ResponseModel doRefill (DoRefill refill) {
        refillBalanceService.refillBalance(
                refill.getAccountNo(),
                refill.getAmount(),
                CurrencyCode.valueOf(refill.getCurrency()));
        responseModel.setMessage("");
        return responseModel;
    }

    public GetTransactionDetails getTransactionDetails (Integer accountId) {

        List<TransactionEntity> entityTransactions = transactionService.findEntityTransactionByAccountNo(accountId);
        GetTransactionDetails transactionDetails = new GetTransactionDetails();

        this.mapper.map(entityTransactions, transactionDetails);
        transactionDetails.setAccountNo(accountId);

        return transactionDetails;

    }

}
