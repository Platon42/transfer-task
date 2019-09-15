package mercy.digital.transfer.dao.impl;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.AccountDao;
import mercy.digital.transfer.dao.BeneficiaryDao;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.sql.SQLException;
import java.util.HashMap;

@Slf4j
public class BeneficiaryDaoImpl implements BeneficiaryDao {

    @Inject
    private AccountDao accountDao;

    @Inject
    private H2DataSourceService h2DataSourceService;
    
    public HashMap<String, Dao<?, ?>> getBeneficiaryDao() {

        Dao<BeneficiaryEntity,Integer> beneficiaryDao;
        HashMap<String,Dao<?,?>> beneficiaryDaoList = new HashMap<>(2);
        try {

            beneficiaryDao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), BeneficiaryEntity.class);

            beneficiaryDaoList.put(AccountEntity.class.getName(), accountDao.getAccountDao());
            beneficiaryDaoList.put(BeneficiaryEntity.class.getName(),beneficiaryDao);
        } catch (SQLException e) {
            log.error("Cannot create Beneficiary DAO" + e.getLocalizedMessage());
        }
        return beneficiaryDaoList;
    }

}
