package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import mercy.digital.transfer.dao.beneficiary.BeneficiaryDao;
import mercy.digital.transfer.dao.beneficiary.BeneficiaryDaoImpl;
import mercy.digital.transfer.dao.beneficiary.account.BeneficiaryAccountDao;
import mercy.digital.transfer.dao.beneficiary.account.BeneficiaryAccountDaoImpl;
import mercy.digital.transfer.dao.client.account.ClientAccountDao;
import mercy.digital.transfer.dao.client.account.ClientAccountDaoImpl;
import mercy.digital.transfer.datasorce.H2DataSource;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.facade.beneficiary.BeneficiaryFacade;
import mercy.digital.transfer.facade.beneficiary.BeneficiaryFacadeImpl;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.service.beneficiary.BeneficiaryServiceImpl;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountServiceImpl;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.client.account.ClientAccountServiceImpl;

public class BeneficiaryFacadeModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(H2DataSourceService.class).to(H2DataSource.class);
        bind(BeneficiaryDao.class).to(BeneficiaryDaoImpl.class);

        bind(ClientAccountDao.class).to(ClientAccountDaoImpl.class);
        bind(ClientAccountService.class).to(ClientAccountServiceImpl.class);

        bind(BeneficiaryAccountDao.class).to(BeneficiaryAccountDaoImpl.class);
        bind(BeneficiaryService.class).to(BeneficiaryServiceImpl.class);
        bind(BeneficiaryAccountService.class).to(BeneficiaryAccountServiceImpl.class);
        bind(BeneficiaryFacade.class).to(BeneficiaryFacadeImpl.class);
    }
}
