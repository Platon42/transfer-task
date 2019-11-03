package mercy.digital.transfer.service.beneficiary.account;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.beneficiary.account.BeneficiaryAccountDao;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.service.client.account.ClientAccountService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class BeneficiaryAccountServiceImpl implements BeneficiaryAccountService {

    @Inject
    private BeneficiaryAccountDao beneficiaryAccountDao;

    @Inject
    private ClientAccountService clientAccountService;

    public Integer addBeneficiaryEntityAccount(BeneficiaryAccountEntity beneficiaryAccountEntity) {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        try {
            //rules for define our clients
            if (beneficiaryAccountEntity.getAccountNo().toString().startsWith("101")) {
                beneficiaryAccountEntity.setClient(true);
                ClientAccountEntity clientEntityAccountByAccountNo =
                        clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountEntity.getAccountNo());
                if (clientEntityAccountByAccountNo == null) {
                    log.error("Not found client with Account No " + beneficiaryAccountEntity.getAccountNo());
                    return null;
                } else {
                    if (!beneficiaryAccountEntity.getCurrency().equals(clientEntityAccountByAccountNo.getCurrency())) {
                        log.warn("Our Client and Beneficiary Accounts has set with different currencies");
                        beneficiaryAccountEntity.setCurrency(clientEntityAccountByAccountNo.getCurrency());
                        log.warn("Beneficiary will be set to currency " + clientEntityAccountByAccountNo.getCurrency());
                    }
                }
            } else {
                beneficiaryAccountEntity.setClient(false);
            }
            accountDao.create(beneficiaryAccountEntity);
            return beneficiaryAccountEntity.getBeneficiaryAccountId();
        } catch (SQLException e) {
            log.error("Cannot add Beneficiary Account entity" + e.getLocalizedMessage());
            return null;
        }
    }

    public BeneficiaryAccountEntity findBeneficiaryEntityAccountById(Integer id) {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        BeneficiaryAccountEntity accountEntity;
        try {
            accountEntity = accountDao.queryForId(id);
            return accountEntity;
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Beneficiary Account entity" + e.getLocalizedMessage());
            return null;
        }
    }

    public BeneficiaryAccountEntity findBeneficiaryEntityAccountByAccountNo(Integer accountNo) {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        QueryBuilder<BeneficiaryAccountEntity, Integer> queryBuilder = accountDao.queryBuilder();
        BeneficiaryAccountEntity beneficiaryAccountEntity;
        try {
            PreparedQuery<BeneficiaryAccountEntity> accountNoQuery =
                    queryBuilder.where().eq("ACCOUNT_NO", accountNo).prepare();
            beneficiaryAccountEntity =  accountDao.query(accountNoQuery).get(0);
            return beneficiaryAccountEntity;
        } catch (IndexOutOfBoundsException | SQLException e) {
            log.error("Cannot find by account No " + accountNo + " Beneficiary Account entity" + e.getLocalizedMessage());
            return null;
        }
    }

    public List<BeneficiaryAccountEntity> findAllEntityBeneficiaryAccounts() {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        List<BeneficiaryAccountEntity> beneficiaryAccountEntityList;
        try {
            beneficiaryAccountEntityList = accountDao.queryForAll();
            return beneficiaryAccountEntityList;
        } catch (SQLException e) {
            log.error("Cannot find Beneficiary Account entity" + e.getLocalizedMessage());
            return Collections.emptyList();
        }
    }
}
