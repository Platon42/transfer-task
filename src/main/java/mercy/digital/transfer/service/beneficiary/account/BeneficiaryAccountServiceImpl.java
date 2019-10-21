package mercy.digital.transfer.service.beneficiary.account;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.beneficiary.account.BeneficiaryAccountDao;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class BeneficiaryAccountServiceImpl implements BeneficiaryAccountService {

    @Inject
    private BeneficiaryAccountDao beneficiaryAccountDao;

    public void addBeneficiaryEntityAccount(BeneficiaryAccountEntity accountEntity) {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        try {
            //rules for define our clients
            if (accountEntity.getAccountNo().toString().startsWith("101")) {
                accountEntity.setClient(true);
            } else {
                accountEntity.setClient(false);
            }
            accountDao.create(accountEntity);
        } catch (SQLException e) {
            log.error("Cannot add Beneficiary Account entity" + e.getLocalizedMessage());
        }
    }

    public BeneficiaryAccountEntity findBeneficiaryEntityAccountById(Integer id) {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        BeneficiaryAccountEntity accountEntity = null;
        try {
            accountEntity = accountDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Beneficiary Account entity" + e.getLocalizedMessage());
        }
        return accountEntity;
    }

    public BeneficiaryAccountEntity findBeneficiaryEntityAccountByAccountNo(Integer accountNo) {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        QueryBuilder<BeneficiaryAccountEntity, Integer> queryBuilder = accountDao.queryBuilder();
        BeneficiaryAccountEntity beneficiaryAccountEntity;
        try {
            PreparedQuery<BeneficiaryAccountEntity> accountNoQuery =
                    queryBuilder.where().eq("ACCOUNT_NO", accountNo).prepare();
            beneficiaryAccountEntity =  accountDao.query(accountNoQuery).get(0);
        } catch (SQLException e) {
            log.error("Cannot find by account No " + accountNo + " Beneficiary Account entity" + e.getLocalizedMessage());
            return null;
        }
        return beneficiaryAccountEntity;
    }

    public List<BeneficiaryAccountEntity> findAllEntityBeneficiaryAccounts() {
        Dao<BeneficiaryAccountEntity, Integer> accountDao = this.beneficiaryAccountDao.getBeneficiaryAccountDao();
        List<BeneficiaryAccountEntity> beneficiaryAccountEntityList = null;
        try {
            beneficiaryAccountEntityList = accountDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Beneficiary Account entity" + e.getLocalizedMessage());
        }
        return beneficiaryAccountEntityList;

    }
}
