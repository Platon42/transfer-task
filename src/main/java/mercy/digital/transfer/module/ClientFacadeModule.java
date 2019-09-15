package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import mercy.digital.transfer.dao.AccountDao;
import mercy.digital.transfer.dao.BeneficiaryDao;
import mercy.digital.transfer.dao.ClientDao;
import mercy.digital.transfer.dao.impl.AccountDaoImpl;
import mercy.digital.transfer.dao.impl.BeneficiaryDaoImpl;
import mercy.digital.transfer.dao.impl.ClientDaoImpl;
import mercy.digital.transfer.datasorce.H2DataSource;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.facade.ClientFacade;
import mercy.digital.transfer.facade.ClientFacadeImpl;
import mercy.digital.transfer.service.AccountService;
import mercy.digital.transfer.service.BeneficiaryService;
import mercy.digital.transfer.service.ClientService;
import mercy.digital.transfer.service.impl.AccountServiceImpl;
import mercy.digital.transfer.service.impl.BeneficiaryServiceImpl;
import mercy.digital.transfer.service.impl.ClientServiceImpl;


public class ClientFacadeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(H2DataSourceService.class).to(H2DataSource.class);
        bind(AccountDao.class).to(AccountDaoImpl.class);
        bind(ClientDao.class).to(ClientDaoImpl.class);
        bind(BeneficiaryDao.class).to(BeneficiaryDaoImpl.class);
        bind(AccountService.class).to(AccountServiceImpl.class);
        bind(ClientService.class).to(ClientServiceImpl.class);
        bind(BeneficiaryService.class).to(BeneficiaryServiceImpl.class);
        bind(ClientFacade.class).to(ClientFacadeImpl.class);
    }
}
