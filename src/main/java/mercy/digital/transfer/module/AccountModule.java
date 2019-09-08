package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import mercy.digital.transfer.dao.AccountDao;
import mercy.digital.transfer.dao.ClientDao;
import mercy.digital.transfer.dao.impl.AccountDaoImpl;
import mercy.digital.transfer.dao.impl.ClientDaoImpl;
import mercy.digital.transfer.service.AccountService;
import mercy.digital.transfer.service.ClientService;
import mercy.digital.transfer.service.impl.AccountServiceImpl;
import mercy.digital.transfer.service.impl.ClientServiceImpl;

public class AccountModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(ClientDao.class).to(ClientDaoImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(ClientService.class).to(ClientServiceImpl.class);
    }
}
