package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.ClientEntity;
import mercy.digital.transfer.facade.client.ClientFacade;
import mercy.digital.transfer.facade.client.account.ClientAccountFacade;
import mercy.digital.transfer.facade.client.account.ClientAccountFacadeImpl;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.presentation.client.AddClient;
import mercy.digital.transfer.presentation.client.GetClient;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;
import mercy.digital.transfer.presentation.transaction.transfer.DoTransfer;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
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
    void doTransfer() {
        DoTransfer doTransfer = new DoTransfer();

        //when()

        //doTransfer.set

        //clientAccountFacade.doTransfer()
    }

    @Test
    void doRefill() {
    }

    @Test
    void getTransactionDetails() {
    }
}