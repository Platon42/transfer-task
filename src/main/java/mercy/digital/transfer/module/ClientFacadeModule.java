package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import mercy.digital.transfer.dao.client.ClientDao;
import mercy.digital.transfer.dao.client.ClientDaoImpl;
import mercy.digital.transfer.dao.client.account.ClientAccountDao;
import mercy.digital.transfer.dao.client.account.ClientAccountDaoImpl;
import mercy.digital.transfer.datasorce.H2DataSource;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.facade.client.ClientFacade;
import mercy.digital.transfer.facade.client.ClientFacadeImpl;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.ClientServiceImpl;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.client.account.ClientAccountServiceImpl;


public class ClientFacadeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(H2DataSourceService.class).to(H2DataSource.class);
        bind(ClientAccountDao.class).to(ClientAccountDaoImpl.class);
        bind(ClientDao.class).to(ClientDaoImpl.class);
        bind(ClientAccountService.class).to(ClientAccountServiceImpl.class);
        bind(ClientService.class).to(ClientServiceImpl.class);
        bind(ClientFacade.class).to(ClientFacadeImpl.class);
    }
}
