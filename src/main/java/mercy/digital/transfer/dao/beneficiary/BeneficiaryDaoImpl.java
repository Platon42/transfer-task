package mercy.digital.transfer.dao.beneficiary;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.sql.SQLException;

@Slf4j
public class BeneficiaryDaoImpl implements BeneficiaryDao {

    @Inject
    private H2DataSourceService h2DataSourceService;

    public Dao<BeneficiaryEntity,Integer> getBeneficiaryDao () {
        Dao<BeneficiaryEntity,Integer> dao;
        try {
            dao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), BeneficiaryEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Beneficiary DAO" + e.getLocalizedMessage());
            return null;
        }
        return dao;
    }

}
