package mercy.digital.transfer.service.beneficiary;

import com.google.inject.Inject;
import com.j256.ormlite.dao.Dao;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.dao.beneficiary.BeneficiaryDao;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class BeneficiaryServiceImpl implements BeneficiaryService {

    @Inject
    private BeneficiaryDao dao;

    public Integer addEntityBeneficiary(BeneficiaryEntity beneficiaryEntity) {
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = this.dao.getBeneficiaryDao();
        try {
            beneficiaryDao.create(beneficiaryEntity);
            return beneficiaryEntity.getBeneficiaryId();
        } catch (SQLException e) {
            log.error("Cannot add Beneficiary entity" + e.getLocalizedMessage());
            return null;
        }
    }

    public BeneficiaryEntity findEntityBeneficiaryById(Integer id) {
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = this.dao.getBeneficiaryDao();
        BeneficiaryEntity beneficiaryEntity;
        try {
            beneficiaryEntity = beneficiaryDao.queryForId(id);
            return beneficiaryEntity;
        } catch (SQLException e) {
            log.error("Cannot find by id " + id + " Beneficiary entity" + e.getLocalizedMessage());
            return null;
        }
    }

    public List<BeneficiaryEntity> findAllEntityBeneficiary() {
        Dao<BeneficiaryEntity, Integer> beneficiaryDao = this.dao.getBeneficiaryDao();
        List<BeneficiaryEntity> beneficiaryEntities;
        try {
            beneficiaryEntities = beneficiaryDao.queryForAll();
            return beneficiaryEntities;
        } catch (SQLException e) {
            log.error("Cannot find Beneficiary entity" + e.getLocalizedMessage());
            return null;
        }
    }
}
