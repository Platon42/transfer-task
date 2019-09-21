package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import mercy.digital.transfer.dao.ClientAccountDao;
import mercy.digital.transfer.dao.BeneficiaryDao;
import mercy.digital.transfer.dao.ClientDao;
import mercy.digital.transfer.dao.impl.ClientAccountDaoImpl;
import mercy.digital.transfer.dao.impl.BeneficiaryDaoImpl;
import mercy.digital.transfer.dao.impl.ClientDaoImpl;
import mercy.digital.transfer.datasorce.H2DataSource;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.facade.ClientFacade;
import mercy.digital.transfer.facade.ClientFacadeImpl;
import mercy.digital.transfer.service.client.ClientAccountService;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.ClientClientAccountServiceImpl;
import mercy.digital.transfer.service.beneficiary.BeneficiaryServiceImpl;
import mercy.digital.transfer.service.client.ClientServiceImpl;


public class ClientFacadeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(H2DataSourceService.class).to(H2DataSource.class);
        bind(ClientAccountDao.class).to(ClientAccountDaoImpl.class);
        bind(ClientDao.class).to(ClientDaoImpl.class);
        bind(BeneficiaryDao.class).to(BeneficiaryDaoImpl.class);
        bind(ClientAccountService.class).to(ClientClientAccountServiceImpl.class);
        bind(ClientService.class).to(ClientServiceImpl.class);
        bind(BeneficiaryService.class).to(BeneficiaryServiceImpl.class);
        bind(ClientFacade.class).to(ClientFacadeImpl.class);
    }
}
