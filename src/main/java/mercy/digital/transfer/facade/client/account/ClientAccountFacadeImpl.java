package mercy.digital.transfer.facade.client.account;

import com.google.inject.Inject;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.presentation.balance.GetBalance;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.response.ResponseServiceBuilder;
import mercy.digital.transfer.presentation.transaction.GetTransaction;
import mercy.digital.transfer.presentation.transaction.GetTransactionDetails;
import mercy.digital.transfer.presentation.transaction.refill.DoRefill;
import mercy.digital.transfer.presentation.transaction.transfer.DoTransfer;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceService;
import mercy.digital.transfer.service.transaction.transfer.TransferService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientAccountFacadeImpl implements ClientAccountFacade {

    private final MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private final MapperFacade mapper = mapperFactory.getMapperFacade();
    private final ResponseModel responseModel = new ResponseModel();

    private final TransferService transferService;
    private final ClientAccountService clientAccountService;
    private final ClientService clientService;
    private final RefillBalanceService refillBalanceService;
    private final TransactionService transactionService;

    @Inject
    public ClientAccountFacadeImpl(TransferService transferService,
                                   ClientAccountService clientAccountService,
                                   ClientService clientService,
                                   RefillBalanceService refillBalanceService,
                                   TransactionService transactionService) {
        this.transferService = transferService;
        this.clientAccountService = clientAccountService;
        this.clientService = clientService;
        this.refillBalanceService = refillBalanceService;
        this.transactionService = transactionService;
    }

    public ResponseModel addClientAccount(AddClientAccount clientAccount) {

        int clientId = clientAccount.getGetClient().getClientId();
        ClientEntity accountById = clientService.findEntityAccountById(clientId);
        ResponseServiceBuilder responseServiceBuilder =
                new ResponseServiceBuilder(responseModel, "addClientAccount");

        if (accountById != null) {
            ClientAccountEntity accountEntity = this.mapper.map(clientAccount, ClientAccountEntity.class);
            accountEntity.setClientByClientId(accountById);
            Integer clientAccountId = clientAccountService.addClientEntityAccount(accountEntity);
            if (clientAccountId == null || clientAccountId == -1) {
                responseServiceBuilder.withMessage("Cannot create client account, with Client Id " +
                        clientId + " see log for details");
                responseServiceBuilder.withStatus(-1);
            } else {
                responseServiceBuilder.withMessage("Success created client Account");
                responseServiceBuilder.withStatus(0);
                responseServiceBuilder.withAdditional("clientAccountId id:" + clientAccountId);
            }
        } else {
            responseServiceBuilder.withMessage("Cannot create client account, with Client Id " +
                    clientId + " see log for details");
            responseServiceBuilder.withStatus(-1);
        }
        responseServiceBuilder.build();
        return responseModel;
    }

    public ResponseModel doTransfer (DoTransfer transfer) {

        ResponseServiceBuilder responseServiceBuilder =
                new ResponseServiceBuilder(responseModel, "doTransfer");

        TransactionStatus transactionStatus = transferService.doTransfer (
                transfer.getAccountNoSender(),
                transfer.getAccountNoReceiver(),
                transfer.getAmount(),
                CurrencyCode.valueOf(transfer.getChangeCurrency())
        );
        responseServiceBuilder.withTransactionStatus(transactionStatus);
        responseServiceBuilder.build();
        return responseModel;
    }

    public ResponseModel doRefill (DoRefill refill) {
        ResponseServiceBuilder responseServiceBuilder =
                new ResponseServiceBuilder(responseModel, "doRefill");

        TransactionStatus transactionStatus = refillBalanceService.refillBalance(
                refill.getAccountNo(),
                refill.getAmount(),
                CurrencyCode.valueOf(refill.getCurrency()));
        responseServiceBuilder.withTransactionStatus(transactionStatus);
        responseServiceBuilder.build();

        return responseModel;
    }

    public GetTransactionDetails getTransactionDetails (Integer accountId) {

        List<TransactionEntity> entityTransactions = transactionService.findEntitiesTransactionByAccountNo(accountId);
        if (entityTransactions.isEmpty()) return null;

        GetTransactionDetails transactionDetails = new GetTransactionDetails();
        List<GetTransaction> getTransactions = new ArrayList<>();

        for (TransactionEntity e : entityTransactions) {
            GetTransaction getTransaction = new GetTransaction();

            getTransaction.setTransactionId(e.getTransactionId());
            getTransaction.setAmount(e.getAmount());
            getTransaction.setCurrency(e.getCurrency());
            getTransaction.setTargetAccountNo(e.getTargetAccountNo());
            getTransaction.setSourceAccountNo(e.getSourceAccountNo());
            getTransaction.setTransactionType(e.getTransactionType());

            getTransactions.add(getTransaction);

        }

        transactionDetails.setTransactionList(getTransactions);
        transactionDetails.setAccountNo(accountId);

        return transactionDetails;
    }
}
