package mercy.digital.transfer.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Generated;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Generated
@DatabaseTable(tableName = "CLIENT_ACCOUNT")
@Entity
@Table(name = "CLIENT_ACCOUNT", schema = "TRANSFER", catalog = "H2")
public class ClientAccountEntity {

    @DatabaseField(generatedId = true, columnName = "CLIENT_ACCOUNT_ID")
    private int clientAccountId;
    @DatabaseField(columnName = "ACCOUNT_NO")
    private Integer accountNo;
    @DatabaseField(columnName = "BALANCE")
    private Double balance;
    @DatabaseField(columnName = "CURRENCY")
    private String currency;
    @DatabaseField(columnName = "CREATED_AT")
    private Timestamp createdAt;
    @DatabaseField(columnName = "UPDATED_AT")
    private Timestamp updatedAt;
    @DatabaseField(columnName = "ACCOUNT_NAME")
    private String accountName;
    @DatabaseField(columnName = "COUNTRY_OF_ISSUE")
    private String countryOfIssue;
    @ForeignCollectionField
    private Collection<BalanceEntity> balanceHistoriesByClientAccountId;

    @DatabaseField(foreign = true, columnName = "CLIENT_ID")
    private ClientEntity clientByClientId;
    private Collection<BalanceEntity> balancesByClientAccountId;

    @Id
    @Column(name = "CLIENT_ACCOUNT_ID", nullable = false)
    public int getClientAccountId() {
        return clientAccountId;
    }

    public void setClientAccountId(int clientAccountId) {
        this.clientAccountId = clientAccountId;
    }

    @Basic
    @Column(name = "ACCOUNT_NO", nullable = false)
    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    @Basic
    @Column(name = "BALANCE", nullable = true, precision = 0)
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "CURRENCY", nullable = true, length = 3)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "CREATED_AT", nullable = true)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "UPDATED_AT", nullable = true)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "ACCOUNT_NAME", nullable = true, length = 50)
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String name) {
        this.accountName = name;
    }

    @Basic
    @Column(name = "COUNTRY_OF_ISSUE", nullable = true, length = 20)
    public String getCountryOfIssue() {
        return countryOfIssue;
    }

    public void setCountryOfIssue(String countryOfIssue) {
        this.countryOfIssue = countryOfIssue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientAccountEntity that = (ClientAccountEntity) o;
        return clientAccountId == that.clientAccountId &&
                accountNo.equals(that.accountNo) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(accountName, that.accountName) &&
                Objects.equals(countryOfIssue, that.countryOfIssue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientAccountId, accountNo, balance, currency, createdAt, updatedAt, accountName, countryOfIssue);
    }

    @OneToMany(mappedBy = "clientAccountByAccountId")
    public Collection<BalanceEntity> getBalanceHistoriesByClientAccountId() {
        return balanceHistoriesByClientAccountId;
    }

    public void setBalanceHistoriesByClientAccountId(Collection<BalanceEntity> balanceHistoriesByClientAccountId) {
        this.balanceHistoriesByClientAccountId = balanceHistoriesByClientAccountId;
    }

    @ManyToOne
    @JoinColumn(name = "CLIENT_ID", referencedColumnName = "CLIENT_ID", nullable = false)
    public ClientEntity getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(ClientEntity clientByClientId) {
        this.clientByClientId = clientByClientId;
    }

    @OneToMany(mappedBy = "clientAccountByAccountId")
    public Collection<BalanceEntity> getBalancesByClientAccountId() {
        return balancesByClientAccountId;
    }

    public void setBalancesByClientAccountId(Collection<BalanceEntity> balancesByClientAccountId) {
        this.balancesByClientAccountId = balancesByClientAccountId;
    }
}
