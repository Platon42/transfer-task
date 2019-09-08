package mercy.digital.transfer.dao.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.db.H2DatabaseType;
import com.j256.ormlite.jdbc.DataSourceConnectionSource;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.BeneficiaryDao;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;
import mercy.digital.transfer.utils.H2DataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class BeneficiaryDaoImpl implements BeneficiaryDao {
    private static DataSourceConnectionSource connectionSource;

    static {
        try {
            DataSource hikariDS = H2DataSource.getDataSource();
            connectionSource = new DataSourceConnectionSource(hikariDS, new H2DatabaseType());
        } catch (SQLException e) {
            log.error(e.getLocalizedMessage());
        }
    }

    private HashMap<String,Dao<?,?>> getBeneficiaryDao () {

        Dao<BeneficiaryEntity,Integer> beneficiaryDao;
        Dao<AccountEntity,Integer> accountDao;
        HashMap<String,Dao<?,?>> beneficiaryDaoList = new HashMap<>(2);
        try {

            accountDao = DaoManager.createDao(connectionSource, AccountEntity.class);
            beneficiaryDao = DaoManager.createDao(connectionSource, BeneficiaryEntity.class);

            beneficiaryDaoList.put(AccountEntity.class.getName(),accountDao);
            beneficiaryDaoList.put(BeneficiaryEntity.class.getName(),beneficiaryDao);
        } catch (SQLException e) {
            log.error("Cannot create Beneficiary DAO" + e.getLocalizedMessage());
        }
        return beneficiaryDaoList;
    }
    public void addBeneficiary (AccountEntity accountEntity, BeneficiaryEntity beneficiaryEntity) {
        @SuppressWarnings("unchecked")
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = (Dao<BeneficiaryEntity, Integer>)
                this.getBeneficiaryDao().get(BeneficiaryEntity.class.getName());
        @SuppressWarnings("unchecked")
        Dao<AccountEntity, Integer> accountDao  = (Dao<AccountEntity, Integer>)
                this.getBeneficiaryDao().get(AccountEntity.class.getName());

        try {
            accountDao.create(accountEntity);
            beneficiaryEntity.setAccountByAccountId(accountEntity);
            beneficiaryDao.create(beneficiaryEntity);
        } catch (SQLException e) {
            log.error("Cannot add Beneficiary entity" + e.getLocalizedMessage());
        }
    }

    public BeneficiaryEntity findBeneficiaryById (Integer id) {
        @SuppressWarnings("unchecked")
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = (Dao<BeneficiaryEntity, Integer>)
                this.getBeneficiaryDao().get(BeneficiaryEntity.class.getName());
        BeneficiaryEntity beneficiaryEntity = null;
        try {
            beneficiaryEntity = beneficiaryDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Beneficiary entity, DB Error:" + e.getLocalizedMessage());
        }
        if (beneficiaryEntity == null) log.warn("No such Beneficiary data for id " + id);
        return beneficiaryEntity;
    }

    public List<BeneficiaryEntity> findAllBeneficiary ()  {
        @SuppressWarnings("unchecked")
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = (Dao<BeneficiaryEntity, Integer>)
                this.getBeneficiaryDao().get(BeneficiaryEntity.class.getName());
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
