package mercy.digital.transfer.domain;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "TRANSFER", schema = "TRANSFER", catalog = "H2")
public class TransferEntity {
    private int transactionId;
    private int sourceAccountId;
    private Integer targetAccountId;
    private Integer amount;
    private String currency;
    private Object createdAt;
    private String transactionType;
    private Collection<BalanceHistoryEntity> balanceHistoriesByTransactionId;

    @Id
    @Column(name = "TRANSACTION_ID", nullable = false)
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "SOURCE_ACCOUNT_ID", nullable = false)
    public int getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(int sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    @Basic
    @Column(name = "TARGET_ACCOUNT_ID", nullable = true)
    public Integer getTargetAccountId() {
        return targetAccountId;
    }

    public void setTargetAccountId(Integer targetAccountId) {
        this.targetAccountId = targetAccountId;
    }

    @Basic
    @Column(name = "AMOUNT", nullable = true)
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "CURRENCY", nullable = false, length = 3)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Basic
    @Column(name = "CREATED_AT", nullable = false)
    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "TRANSACTION_TYPE", nullable = true, length = 20)
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferEntity that = (TransferEntity) o;
        return transactionId == that.transactionId &&
                sourceAccountId == that.sourceAccountId &&
                Objects.equals(targetAccountId, that.targetAccountId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(transactionType, that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, sourceAccountId, targetAccountId, amount, currency, createdAt, transactionType);
    }

    @OneToMany(mappedBy = "transferByTransactionId")
    public Collection<BalanceHistoryEntity> getBalanceHistoriesByTransactionId() {
        return balanceHistoriesByTransactionId;
    }

    public void setBalanceHistoriesByTransactionId(Collection<BalanceHistoryEntity> balanceHistoriesByTransactionId) {
        this.balanceHistoriesByTransactionId = balanceHistoriesByTransactionId;
    }
}
