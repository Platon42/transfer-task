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
@DatabaseTable(tableName = "TRANSACTION")
@Entity
@Table(name = "TRANSACTION", schema = "TRANSFER", catalog = "H2")
public class TransactionEntity {

    @DatabaseField(generatedId = true, columnName = "TRANSACTION_ID")
    private int transactionId;

    @DatabaseField(columnName = "SOURCE_ACCOUNT_NO")
    private Integer sourceAccountNo;
    @DatabaseField(columnName = "TARGET_ACCOUNT_NO")
    private Integer targetAccountNo;
    @DatabaseField(columnName = "AMOUNT")
    private Double amount;
    @DatabaseField(columnName = "CURRENCY")
    private String currency;
    @DatabaseField(columnName = "CREATED_AT")
    private Timestamp createdAt;
    @DatabaseField(columnName = "TRANSACTION_TYPE")
    private String transactionType;

    @ForeignCollectionField
    private Collection<BalanceEntity> balancesByTransactionId;

    @Id
    @Column(name = "TRANSACTION_ID", nullable = false)
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    @Basic
    @Column(name = "SOURCE_ACCOUNT_NO", nullable = false)
    public Integer getSourceAccountNo() {
        return sourceAccountNo;
    }

    public void setSourceAccountNo(int sourceAccountNo) {
        this.sourceAccountNo = sourceAccountNo;
    }

    public void setSourceAccountNo(Integer sourceAccountNo) {
        this.sourceAccountNo = sourceAccountNo;
    }

    @Basic
    @Column(name = "TARGET_ACCOUNT_NO", nullable = true)
    public Integer getTargetAccountNo() {
        return targetAccountNo;
    }

    public void setTargetAccountNo(Integer targetAccountNo) {
        this.targetAccountNo = targetAccountNo;
    }

    @Basic
    @Column(name = "AMOUNT", nullable = true)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
        TransactionEntity that = (TransactionEntity) o;
        return transactionId == that.transactionId &&
                sourceAccountNo.equals(that.sourceAccountNo) &&
                Objects.equals(targetAccountNo, that.targetAccountNo) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(currency, that.currency) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(transactionType, that.transactionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, sourceAccountNo, targetAccountNo, amount, currency, createdAt, transactionType);
    }


    @OneToMany(mappedBy = "transactionByTransactionId")
    public Collection<BalanceEntity> getBalancesByTransactionId() {
        return balancesByTransactionId;
    }

    public void setBalancesByTransactionId(Collection<BalanceEntity> balancesByTransactionId) {
        this.balancesByTransactionId = balancesByTransactionId;
    }
}
