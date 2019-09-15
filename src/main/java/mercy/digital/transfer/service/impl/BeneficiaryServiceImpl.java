package mercy.digital.transfer.service.impl;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.BeneficiaryDao;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;
import mercy.digital.transfer.service.BeneficiaryService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Inject
    private BeneficiaryDao beneficiaryDao;

    public void addEntityBeneficiary(AccountEntity accountEntity, BeneficiaryEntity beneficiaryEntity) {
        @SuppressWarnings("unchecked")
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = (Dao<BeneficiaryEntity, Integer>)
                this.beneficiaryDao.getBeneficiaryDao().get(BeneficiaryEntity.class.getName());
        @SuppressWarnings("unchecked")
        Dao<AccountEntity, Integer> accountDao = (Dao<AccountEntity, Integer>)
                this.beneficiaryDao.getBeneficiaryDao().get(AccountEntity.class.getName());

        try {
            accountDao.create(accountEntity);
            beneficiaryEntity.setAccountByAccountId(accountEntity);
            beneficiaryDao.create(beneficiaryEntity);
        } catch (SQLException e) {
            log.error("Cannot add Beneficiary entity" + e.getLocalizedMessage());
        }
    }

    public BeneficiaryEntity findEntityBeneficiaryById(Integer id) {
        @SuppressWarnings("unchecked")
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = (Dao<BeneficiaryEntity, Integer>)
                this.beneficiaryDao.getBeneficiaryDao().get(BeneficiaryEntity.class.getName());
        BeneficiaryEntity beneficiaryEntity = null;
        try {
            beneficiaryEntity = beneficiaryDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Beneficiary entity, DB Error:" + e.getLocalizedMessage());
        }
        if (beneficiaryEntity == null) log.warn("No such Beneficiary data for id " + id);
        return beneficiaryEntity;
    }

    public List<BeneficiaryEntity> findAllEntityBeneficiary() {
        @SuppressWarnings("unchecked")
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = (Dao<BeneficiaryEntity, Integer>)
                this.beneficiaryDao.getBeneficiaryDao().get(BeneficiaryEntity.class.getName());
        List<BeneficiaryEntity> beneficiaryEntityList = null;
        try {
            beneficiaryEntityList = beneficiaryDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find all Beneficiary entity" + e.getLocalizedMessage());
        }
        if (beneficiaryEntityList == null) log.warn("Table Beneficiary is empty");
        return beneficiaryEntityList;
    }

}
