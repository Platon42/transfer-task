package mercy.digital.transfer.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNT", schema = "PUBLIC", catalog = "TRANSFER_DB")
public class AccountEntity {
    private int accountId;
    private Integer accountNo;
    private Double balance;
    private String currency;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String name;
    private BeneficiaryEntity beneficiaryByBeneficiaryId;
    private Collection<BalanceHistoryEntity> balanceHistoriesByAccountId;
    private Collection<BeneficiaryEntity> beneficiariesByAccountId;
    private Collection<TransactionHistoryEntity> transactionHistories;

    @Id
    @Column(name = "ACCOUNT_ID")
    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    @Basic
    @Column(name = "ACCOUNT_NO")
    public Integer getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(Integer accountNo) {
        this.accountNo = accountNo;
    }

    @Basic
    @Column(name = "BALANCE")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Basic
    @Column(name = "CURRENCY")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "CREATED_AT")
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "UPDATED_AT")
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return accountId == that.accountId &&
                Objects.equals(accountNo, that.accountNo) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountNo, balance, currency, createdAt, updatedAt, name);
    }

    @ManyToOne
    @JoinColumn(name = "BENEFICIARY_ID", referencedColumnName = "BENEFICIARY_ID")
    public BeneficiaryEntity getBeneficiaryByBeneficiaryId() {
        return beneficiaryByBeneficiaryId;
    }

    public void setBeneficiaryByBeneficiaryId(BeneficiaryEntity beneficiaryByBeneficiaryId) {
        this.beneficiaryByBeneficiaryId = beneficiaryByBeneficiaryId;
    }

    @OneToMany(mappedBy = "accountByAccountId")
    public Collection<BalanceHistoryEntity> getBalanceHistoriesByAccountId() {
        return balanceHistoriesByAccountId;
    }

    public void setBalanceHistoriesByAccountId(Collection<BalanceHistoryEntity> balanceHistoriesByAccountId) {
        this.balanceHistoriesByAccountId = balanceHistoriesByAccountId;
    }

    @OneToMany(mappedBy = "accountByAccountId")
    public Collection<BeneficiaryEntity> getBeneficiariesByAccountId() {
        return beneficiariesByAccountId;
    }

    public void setBeneficiariesByAccountId(Collection<BeneficiaryEntity> beneficiariesByAccountId) {
        this.beneficiariesByAccountId = beneficiariesByAccountId;
    }

    @OneToMany(mappedBy = "account")
    public Collection<TransactionHistoryEntity> getTransactionHistories() {
        return transactionHistories;
    }

    public void setTransactionHistories(Collection<TransactionHistoryEntity> transactionHistories) {
        this.transactionHistories = transactionHistories;
    }
}
