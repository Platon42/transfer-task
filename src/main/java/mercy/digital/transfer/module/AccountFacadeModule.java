package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import mercy.digital.transfer.dao.client.ClientDao;
import mercy.digital.transfer.dao.client.ClientDaoImpl;
import mercy.digital.transfer.dao.client.account.ClientAccountDao;
import mercy.digital.transfer.dao.client.account.ClientAccountDaoImpl;
import mercy.digital.transfer.datasorce.H2DataSource;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.facade.account.ClientAccountFacade;
import mercy.digital.transfer.facade.account.ClientAccountFacadeImpl;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.balance.BalanceServiceImpl;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.ClientServiceImpl;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.client.account.ClientAccountServiceImpl;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.TransactionServiceImpl;
import mercy.digital.transfer.service.transfer.TransferService;
import mercy.digital.transfer.service.transfer.TransferServiceImpl;

public class AccountFacadeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(H2DataSourceService.class).to(H2DataSource.class);
        bind(ClientAccountDao.class).to(ClientAccountDaoImpl.class);
        bind(ClientDao.class).to(ClientDaoImpl.class);
        bind(ClientAccountService.class).to(ClientAccountServiceImpl.class);
        bind(ClientService.class).to(ClientServiceImpl.class);
        bind(TransferService.class).to(TransferServiceImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);
        bind(BalanceService.class).to(BalanceServiceImpl.class);
        bind(ClientAccountFacade.class).to(ClientAccountFacadeImpl.class);
    }
}