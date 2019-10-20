package mercy.digital.transfer.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;
import java.util.Objects;

@DatabaseTable(tableName = "BENEFICIARY_ACCOUNT")
@Entity
@Table(name = "BENEFICIARY_ACCOUNT", schema = "TRANSFER", catalog = "H2")
public class BeneficiaryAccountEntity {

    @DatabaseField(generatedId = true, columnName = "BENEFICIARY_ACCOUNT_ID")
    private int beneficiaryAccountId;

    @DatabaseField(columnName = "ACCOUNT_NO")
    private Long accountNo;

    @DatabaseField(columnName = "NAME")
    private String name;

    @DatabaseField(columnName = "IS_CLIENT")
    private Boolean isClient;

    @DatabaseField(foreign = true, columnName = "BENEFICIARY_ID")
    private BeneficiaryEntity beneficiaryByBeneficiaryId;

    @DatabaseField(columnName = "CURRENCY")
    private String currency;

    @Id
    @Column(name = "BENEFICIARY_ACCOUNT_ID", nullable = false)
    public int getBeneficiaryAccountId() {
        return beneficiaryAccountId;
    }

    public void setBeneficiaryAccountId(int beneficiaryAccountId) {
        this.beneficiaryAccountId = beneficiaryAccountId;
    }

    @Basic
    @Column(name = "ACCOUNT_NO", nullable = false)
    public Long getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Long accountNo) {
        this.accountNo = accountNo;
    }

    @Basic
    @Column(name = "NAME", nullable = true, length = 50)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "IS_CLIENT", nullable = true)
    public Boolean getClient() {
        return isClient;
    }

    public void setClient(Boolean client) {
        isClient = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeneficiaryAccountEntity that = (BeneficiaryAccountEntity) o;
        return beneficiaryAccountId == that.beneficiaryAccountId &&
                accountNo.equals(that.accountNo) &&
                Objects.equals(name, that.name) &&
                Objects.equals(isClient, that.isClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beneficiaryAccountId, accountNo, name, isClient);
    }

    @ManyToOne
    @JoinColumn(name = "BENEFICIARY_ID", referencedColumnName = "BENEFICIARY_ID")
    public BeneficiaryEntity getBeneficiaryByBeneficiaryId() {
        return beneficiaryByBeneficiaryId;
    }

    public void setBeneficiaryByBeneficiaryId(BeneficiaryEntity beneficiaryByBeneficiaryId) {
        this.beneficiaryByBeneficiaryId = beneficiaryByBeneficiaryId;
    }

    @Basic
    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
