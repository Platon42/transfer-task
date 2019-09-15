package mercy.digital.transfer.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TRANSACTION_HISTORY", schema = "TRANSFER")
public class TransactionHistoryEntity {
    private int transactionId;
    private Integer amount;
    private String currency;
    private Object createdAt;
    private Integer tType;
    private AccountEntity account;

    @Id
    @Column(name = "TRANSACTION_ID")
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "AMOUNT")
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
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
    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "T_TYPE")
    public Integer gettType() {
        return tType;
    }

    public void settType(Integer tType) {
        this.tType = tType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionHistoryEntity that = (TransactionHistoryEntity) o;
        return transactionId == that.transactionId &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(tType, that.tType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, amount, currency, createdAt, tType);
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "SOURCE_ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID", nullable = false), @JoinColumn(name = "TARGET_ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")})
    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
