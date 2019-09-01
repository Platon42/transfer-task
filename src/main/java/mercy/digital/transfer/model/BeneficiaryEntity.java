package mercy.digital.transfer.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "BENEFICIARY", schema = "PUBLIC", catalog = "TRANSFER_DB")
public class BeneficiaryEntity {
    private int beneficiaryId;
    private String streetLine;
    private String city;
    private String country;
    private String postcode;
    private Collection<AccountEntity> accountsByBeneficiaryId;
    private ClientEntity clientByClientId;
    private AccountEntity accountByAccountId;

    @Id
    @Column(name = "BENEFICIARY_ID")
    public int getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(int beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    @Basic
    @Column(name = "STREET_LINE")
    public String getStreetLine() {
        return streetLine;
    }

    public void setStreetLine(String streetLine) {
        this.streetLine = streetLine;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "POSTCODE")
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
    public Collection<AccountEntity> getAccountsByBeneficiaryId() {
        return accountsByBeneficiaryId;
    }

    public void setAccountsByBeneficiaryId(Collection<AccountEntity> accountsByBeneficiaryId) {
        this.accountsByBeneficiaryId = accountsByBeneficiaryId;
    }

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID")
    public ClientEntity getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(ClientEntity clientByClientId) {
        this.clientByClientId = clientByClientId;
    }

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
    public AccountEntity getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(AccountEntity accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }
}
