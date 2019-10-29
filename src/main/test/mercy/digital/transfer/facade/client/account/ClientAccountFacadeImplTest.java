package mercy.digital.transfer.facade.client.account;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.presentation.client.GetClient;
import mercy.digital.transfer.presentation.client.account.AddClientAccount;
import mercy.digital.transfer.presentation.transaction.transfer.DoTransfer;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceService;
import mercy.digital.transfer.service.transaction.transfer.TransferService;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(AccountFacadeModule.class)
class ClientAccountFacadeImplTest {

    private static final Integer CLIENT_ACCOUNT_ID = 10;

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    private AddClientAccount addClientAccountStub;
    private ClientAccountFacade clientAccountFacade;

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

    @BeforeEach
    void setUp() {

        addClientAccountStub = new AddClientAccount();

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

    @AfterEach
    void tearDown() {
    }

    @Test
    void addClientAccount() {

        GetClient getClient = new GetClient();
        getClient.setClientId(100);
        addClientAccountStub.setGetClient(getClient);

        when(clientAccountService.addClientEntityAccount(any())).thenReturn(CLIENT_ACCOUNT_ID);
        clientAccountFacade.addClientAccount(addClientAccountStub);
    }

    void doTransfer() {
        DoTransfer doTransfer = new DoTransfer();
        doTransfer.setAccountNoReceiver(1);
        doTransfer.setAccountNoSender(2);
        doTransfer.setAmount(80.0);
        doTransfer.setChangeCurrency(CurrencyCode.RUB.name());

        ClientAccountEntity sender = new ClientAccountEntity();
        sender.setAccountNo(2);

        //when(clientAccountService.addClientEntityAccount())

        clientAccountFacade.doTransfer(doTransfer);

    }

    @Test
    void doRefill() {
    }

    @Test
    void getTransactionDetails() {
    }
}