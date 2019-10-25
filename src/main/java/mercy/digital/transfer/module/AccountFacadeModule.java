package mercy.digital.transfer.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import mercy.digital.transfer.dao.balance.BalanceDao;
import mercy.digital.transfer.dao.balance.BalanceDaoImpl;
import mercy.digital.transfer.dao.beneficiary.account.BeneficiaryAccountDao;
import mercy.digital.transfer.dao.beneficiary.account.BeneficiaryAccountDaoImpl;
import mercy.digital.transfer.dao.client.ClientDao;
import mercy.digital.transfer.dao.client.ClientDaoImpl;
import mercy.digital.transfer.dao.client.account.ClientAccountDao;
import mercy.digital.transfer.dao.client.account.ClientAccountDaoImpl;
import mercy.digital.transfer.dao.transaction.TransactionDao;
import mercy.digital.transfer.dao.transaction.TransactionDaoImpl;
import mercy.digital.transfer.datasorce.H2DataSource;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.facade.client.account.ClientAccountFacade;
import mercy.digital.transfer.facade.client.account.ClientAccountFacadeImpl;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.balance.BalanceServiceImpl;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountServiceImpl;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.ClientServiceImpl;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.client.account.ClientAccountServiceImpl;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.TransactionServiceImpl;
import mercy.digital.transfer.service.transaction.converter.ConverterService;
import mercy.digital.transfer.service.transaction.converter.ConverterServiceImpl;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceService;
import mercy.digital.transfer.service.transaction.refill.RefillBalanceServiceImpl;
import mercy.digital.transfer.service.transaction.transfer.TransferService;
import mercy.digital.transfer.service.transaction.transfer.TransferServiceImpl;

public class AccountFacadeModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(H2DataSourceService.class).to(H2DataSource.class).in(Singleton.class);
        bind(ClientAccountDao.class).to(ClientAccountDaoImpl.class);
        bind(BeneficiaryAccountDao.class).to(BeneficiaryAccountDaoImpl.class);
        bind(ClientDao.class).to(ClientDaoImpl.class);
        bind(BalanceDao.class).to(BalanceDaoImpl.class);
        bind(TransactionDao.class).to(TransactionDaoImpl.class);
        bind(ClientAccountService.class).to(ClientAccountServiceImpl.class);
        bind(BeneficiaryAccountService.class).to(BeneficiaryAccountServiceImpl.class);
        bind(ClientService.class).to(ClientServiceImpl.class);
        bind(TransferService.class).to(TransferServiceImpl.class);
        bind(TransactionService.class).to(TransactionServiceImpl.class);
        bind(BalanceService.class).to(BalanceServiceImpl.class);
        bind(ConverterService.class).to(ConverterServiceImpl.class);
        bind(RefillBalanceService.class).to(RefillBalanceServiceImpl.class);
        bind(ClientAccountFacade.class).to(ClientAccountFacadeImpl.class);
    }
}