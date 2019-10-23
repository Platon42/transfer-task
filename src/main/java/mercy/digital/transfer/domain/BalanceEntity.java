package mercy.digital.transfer.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javax.persistence.*;
import java.util.Objects;

@DatabaseTable(tableName = "BALANCE")
@Entity
@Table(name = "BALANCE", schema = "TRANSFER", catalog = "H2")
public class BalanceEntity {

    @DatabaseField(columnName = "BALANCE_ID", generatedId = true)
    private int balanceId;

    @DatabaseField(columnName = "BEFORE_BALANCE")
    private Double beforeBalance;

    @DatabaseField(columnName = "PAST_BALANCE")
    private Double pastBalance;

    @DatabaseField(foreign = true, columnName = "ACCOUNT_ID")
    private ClientAccountEntity clientAccountByAccountId;

    @DatabaseField(foreign = true, columnName = "TRANSACTION_ID")
    private TransactionEntity transactionByTransactionId;

    @Id
    @Column(name = "BALANCE_ID", nullable = false)
    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    @Basic
    @Column(name = "BEFORE_BALANCE", nullable = true, precision = 0)
    public Double getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(Double beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    @Basic
    @Column(name = "PAST_BALANCE", nullable = true, precision = 0)
    public Double getPastBalance() {
        return pastBalance;
    }

    public void setPastBalance(Double pastBalance) {
        this.pastBalance = pastBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceEntity that = (BalanceEntity) o;
        return balanceId == that.balanceId &&
                Objects.equals(beforeBalance, that.beforeBalance) &&
                Objects.equals(pastBalance, that.pastBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceId, beforeBalance, pastBalance);
    }

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "CLIENT_ACCOUNT_ID")
    public ClientAccountEntity getClientAccountByAccountId() {
        return clientAccountByAccountId;
    }

    public void setClientAccountByAccountId(ClientAccountEntity clientAccountByAccountId) {
        this.clientAccountByAccountId = clientAccountByAccountId;
    }

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID", referencedColumnName = "TRANSACTION_ID")
    public TransactionEntity getTransactionByTransactionId() {
        return transactionByTransactionId;
    }

    public void setTransactionByTransactionId(TransactionEntity transactionByTransactionId) {
        this.transactionByTransactionId = transactionByTransactionId;
    }
}
