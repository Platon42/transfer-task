package mercy.digital.transfer.dao.beneficiary.account;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.datasorce.H2DataSourceService;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;

import java.sql.SQLException;

@Slf4j
public class BeneficiaryAccountDaoImpl implements BeneficiaryAccountDao {

    @Inject
    private H2DataSourceService h2DataSourceService;

    public Dao<BeneficiaryAccountEntity, Integer> getBeneficiaryAccountDao() {
        Dao<BeneficiaryAccountEntity, Integer> dao = null;
        try {
            dao = DaoManager.createDao(h2DataSourceService.getConnectionSource(), BeneficiaryAccountEntity.class);
        } catch (SQLException e) {
            log.error("Cannot create Beneficiary Account DAO " + e.getLocalizedMessage());
        }
        return dao;
    }
}
