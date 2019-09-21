package mercy.digital.transfer.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "BENEFICIARY", schema = "TRANSFER", catalog = "H2")
public class BeneficiaryEntity {
    private int beneficiaryId;
    private String streetLine;
    private String city;
    private String country;
    private String postcode;
    private Collection<BeneficiaryAccountEntity> beneficiaryAccountsByBeneficiaryId;

    @Id
    @Column(name = "BENEFICIARY_ID", nullable = false)
    public int getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(int beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    @Basic
    @Column(name = "STREET_LINE", nullable = true, length = 50)
    public String getStreetLine() {
        return streetLine;
    }

    public void setStreetLine(String streetLine) {
        this.streetLine = streetLine;
    }

    @Basic
    @Column(name = "CITY", nullable = true, length = 50)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "COUNTRY", nullable = true, length = 3)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "POSTCODE", nullable = true, length = 20)
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeneficiaryEntity that = (BeneficiaryEntity) o;
        return beneficiaryId == that.beneficiaryId &&
                Objects.equals(streetLine, that.streetLine) &&
                Objects.equals(city, that.city) &&
                Objects.equals(country, that.country) &&
                Objects.equals(postcode, that.postcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beneficiaryId, streetLine, city, country, postcode);
    }

    @OneToMany(mappedBy = "beneficiaryByBeneficiaryId")
    public Collection<BeneficiaryAccountEntity> getBeneficiaryAccountsByBeneficiaryId() {
        return beneficiaryAccountsByBeneficiaryId;
    }

    public void setBeneficiaryAccountsByBeneficiaryId(Collection<BeneficiaryAccountEntity> beneficiaryAccountsByBeneficiaryId) {
        this.beneficiaryAccountsByBeneficiaryId = beneficiaryAccountsByBeneficiaryId;
    }
}
