package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import mercy.digital.transfer.dao.AccountDao;
import mercy.digital.transfer.dao.impl.AccountDaoImpl;
import mercy.digital.transfer.datasorce.H2DataSource;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.facade.AccountFacade;
import mercy.digital.transfer.facade.AccountFacadeImpl;
import mercy.digital.transfer.service.AccountService;
import mercy.digital.transfer.service.BalanceService;
import mercy.digital.transfer.service.TransactionService;
import mercy.digital.transfer.service.TransferService;
import mercy.digital.transfer.service.impl.AccountServiceImpl;
import mercy.digital.transfer.service.impl.BalanceServiceImpl;
import mercy.digital.transfer.service.impl.TransactionServiceImpl;
import mercy.digital.transfer.service.impl.TransferServiceImpl;

public class AccountFacadeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(H2DataSourceService.class).to(H2DataSource.class);
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(TransferService.class).to(TransferServiceImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);
        bind(BalanceService.class).to(BalanceServiceImpl.class);
        bind(AccountFacade.class).to(AccountFacadeImpl.class);
    }
}