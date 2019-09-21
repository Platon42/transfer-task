package mercy.digital.transfer.service.beneficiary;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.BeneficiaryDao;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Inject
    private BeneficiaryDao beneficiaryDao;


    public void addEntityBeneficiary(BeneficiaryEntity beneficiaryEntity) {
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = this.beneficiaryDao.getBeneficiaryDao();
        try {
            beneficiaryDao.create(beneficiaryEntity);
        } catch (SQLException e) {
            log.error("Cannot add Beneficiary entity" + e.getLocalizedMessage());
        }
    }

    public BeneficiaryEntity findEntityBeneficiaryById(Integer id) {
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = this.beneficiaryDao.getBeneficiaryDao();
        BeneficiaryEntity beneficiaryEntity = null;
        try {
            beneficiaryEntity = beneficiaryDao.queryForId(id);
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Beneficiary entity" + e.getLocalizedMessage());
        }
        return beneficiaryEntity;
    }

    public List<BeneficiaryEntity> findAllEntityBeneficiary() {
        Dao<BeneficiaryEntity, Integer> beneficiaryDao  = this.beneficiaryDao.getBeneficiaryDao();
        List<BeneficiaryEntity> beneficiaryEntities = null;
        try {
            beneficiaryEntities = beneficiaryDao.queryForAll();
        } catch (SQLException e) {
            log.error("Cannot find Beneficiary entity" + e.getLocalizedMessage());
        }
        return beneficiaryEntities;

    }

}
