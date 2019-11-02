package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.facade.client.ClientFacade;
import mercy.digital.transfer.facade.client.account.ClientAccountFacade;
import mercy.digital.transfer.facade.client.account.ClientAccountFacadeImpl;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.client.GetClient;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transaction.refill.DoRefill;
import mercy.digital.transfer.presentation.transaction.transfer.DoTransfer;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceService;
import mercy.digital.transfer.service.transaction.transfer.TransferService;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(AccountFacadeModule.class)
@IncludeModule(BeneficiaryFacadeModule.class)

class ClientAccountFacadeImplTest {

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    private AddClient addClientStub;
    private AddClientAccount addClientAccountStub;
    private ClientAccountFacade clientAccountFacade;

    private ClientFacade clientFacade;

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

    @BeforeAll
    void setUp() {

        addClientAccountStub = new AddClientAccount();
        GetClient getClient = new GetClient();
        getClient.setClientId(1);
        addClientAccountStub.setGetClient(getClient);


        addClientStub = new AddClient();
        transferService = Mockito.mock(TransferService.class);
        clientAccountService = Mockito.mock(ClientAccountService.class);
        clientService = Mockito.mock(ClientService.class);
        refillBalanceService = Mockito.mock(RefillBalanceService.class);
        transactionService = Mockito.mock(TransactionService.class);

        clientAccountFacade = new ClientAccountFacadeImpl(
                transferService,
                clientAccountService,
                clientService,
                refillBalanceService,
                transactionService);
    }

    @Test
    @Order(1)
    void addClientAccountSuccess() {

        when(clientService.findEntityAccountById(any())).thenReturn(new ClientEntity());
        when(clientAccountService.addClientEntityAccount(any())).thenReturn(1);

        clientAccountFacade.addClientAccount(addClientAccountStub);

        ResponseModel expectedModel = new ResponseModel();
        expectedModel.setStatus(0);
        ResponseModel actualModel = clientAccountFacade.addClientAccount(addClientAccountStub);

        Assertions.assertEquals(expectedModel.getStatus(), actualModel.getStatus());

    }

    @Test
    @Order(2)
    void addClientAccountNotFoundClient() {

        when(clientService.findEntityAccountById(any())).thenReturn(null);
        when(clientAccountService.addClientEntityAccount(any())).thenReturn(1);

        clientAccountFacade.addClientAccount(addClientAccountStub);

        ResponseModel expectedModel = new ResponseModel();
        expectedModel.setStatus(-1);
        ResponseModel actualModel = clientAccountFacade.addClientAccount(addClientAccountStub);

        Assertions.assertEquals(expectedModel.getStatus(), actualModel.getStatus());

    }

    @Test
    @Order(3)
    void addClientAccountCannotAddEntity() {

        when(clientService.findEntityAccountById(any())).thenReturn(new ClientEntity());
        when(clientAccountService.addClientEntityAccount(any())).thenReturn(null);

        clientAccountFacade.addClientAccount(addClientAccountStub);

        ResponseModel expectedModel = new ResponseModel();
        expectedModel.setStatus(-1);
        ResponseModel actualModel = clientAccountFacade.addClientAccount(addClientAccountStub);

        Assertions.assertEquals(expectedModel.getStatus(), actualModel.getStatus());

    }

    @Test
    @Order(4)
    void doTransferSuccess() {
        DoTransfer doTransfer = new DoTransfer();
        Integer clientAccountNo = 101111;
        Integer beneficiaryAccountNo = 101112;
        CurrencyCode transferCurrency = CurrencyCode.RUB;
        Double amount = 100.0;

        doTransfer.setChangeCurrency(transferCurrency.name());
        doTransfer.setAmount(amount);
        doTransfer.setAccountNoSender(clientAccountNo);
        doTransfer.setAccountNoReceiver(beneficiaryAccountNo);

        when(transferService.doTransfer(clientAccountNo, beneficiaryAccountNo, amount, transferCurrency)).
                thenReturn(TransactionStatus.TRANSFER_COMPLETED);
        ResponseModel responseModel = clientAccountFacade.doTransfer(doTransfer);
        Assertions.assertEquals(0, responseModel.getStatus());
    }

    @Test
    @Order(5)
    void doTransferIncorrect() {
        DoTransfer doTransfer = new DoTransfer();
        Integer clientAccountNo = 101111;
        Integer beneficiaryAccountNo = 101112;
        CurrencyCode transferCurrency = CurrencyCode.RUB;
        Double amount = 100.0;

        doTransfer.setChangeCurrency(transferCurrency.name());
        doTransfer.setAmount(amount);
        doTransfer.setAccountNoSender(clientAccountNo);
        doTransfer.setAccountNoReceiver(beneficiaryAccountNo);

        ResponseModel responseModel;

        when(transferService.doTransfer(clientAccountNo, beneficiaryAccountNo, amount, transferCurrency)).
                thenReturn(TransactionStatus.INCORRECT_AMOUNT);
        responseModel = clientAccountFacade.doTransfer(doTransfer);
        Assertions.assertEquals(-1, responseModel.getStatus());

        when(transferService.doTransfer(clientAccountNo, beneficiaryAccountNo, amount, transferCurrency)).
                thenReturn(TransactionStatus.INSUFFICIENT_FUNDS);
        responseModel = clientAccountFacade.doTransfer(doTransfer);
        Assertions.assertEquals(-1, responseModel.getStatus());

    }

    @Test
    @Order(6)
    void doRefillSuccess() {
        DoRefill doRefill = new DoRefill();

        Integer clientAccountNo = 101111;
        CurrencyCode chargeCurrency = CurrencyCode.RUB;
        Double amount = 100.0;

        doRefill.setAccountNo(clientAccountNo);
        doRefill.setAmount(amount);
        doRefill.setCurrency(chargeCurrency.name());

        when(refillBalanceService.refillBalance(clientAccountNo, amount, chargeCurrency)).thenReturn(TransactionStatus.REFILL_COMPLETED);
        ResponseModel responseModel = clientAccountFacade.doRefill(doRefill);
        Assertions.assertEquals(0, responseModel.getStatus());
    }

    @Test
    @Order(7)
    void doRefillIncorrect() {
        DoRefill doRefill = new DoRefill();

        Integer clientAccountNo = 101111;
        CurrencyCode chargeCurrency = CurrencyCode.RUB;
        Double amount = 100.0;

        doRefill.setAccountNo(clientAccountNo);
        doRefill.setAmount(amount);
        doRefill.setCurrency(chargeCurrency.name());
        ResponseModel responseModel;

        when(refillBalanceService.refillBalance(clientAccountNo, amount, chargeCurrency)).thenReturn(TransactionStatus.INCORRECT_AMOUNT);
        responseModel = clientAccountFacade.doRefill(doRefill);
        Assertions.assertEquals(-1, responseModel.getStatus());

        when(refillBalanceService.refillBalance(clientAccountNo, amount, chargeCurrency)).thenReturn(TransactionStatus.ERROR_OCCURRED);
        responseModel = clientAccountFacade.doRefill(doRefill);
        Assertions.assertEquals(-1, responseModel.getStatus());
    }

    @Test
    void getTransactionDetails() {

    }
}