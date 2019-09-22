package mercy.digital.transfer.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@DatabaseTable(tableName = "TRANSFER")
@Entity
@Table(name = "TRANSFER", schema = "TRANSFER", catalog = "H2")
public class TransferEntity {

    @DatabaseField(generatedId = true, columnName = "TRANSACTION_ID")
    private int transactionId;

    @DatabaseField(columnName = "SOURCE_ACCOUNT_ID")
    private int sourceAccountId;

    @DatabaseField(columnName = "TARGET_ACCOUNT_ID")
    private Integer targetAccountId;

    @DatabaseField(columnName = "AMOUNT")
    private Integer amount;

    @DatabaseField(columnName = "CURRENCY")
    private String currency;

    @DatabaseField(columnName = "CREATED_AT")
    private Timestamp createdAt;

    @DatabaseField(columnName = "TRANSACTION_TYPE")
    private String transactionType;

    @ForeignCollectionField
    private Collection<BalanceEntity> balanceHistoriesByTransactionId;

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
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
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
    public Collection<BalanceEntity> getBalanceHistoriesByTransactionId() {
        return balanceHistoriesByTransactionId;
    }

    public void setBalanceHistoriesByTransactionId(Collection<BalanceEntity> balanceHistoriesByTransactionId) {
        this.balanceHistoriesByTransactionId = balanceHistoriesByTransactionId;
    }
}
