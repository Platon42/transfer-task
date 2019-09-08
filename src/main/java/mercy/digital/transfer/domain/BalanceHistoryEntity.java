package mercy.digital.transfer.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BALANCE_HISTORY", schema = "TRANSFER", catalog = "TRANSFER_DB")
public class BalanceHistoryEntity {
    private int balanceId;
    private Double beforeBalance;
    private Double pastBalance;
    private AccountEntity accountByAccountId;

    @Id
    @Column(name = "BALANCE_ID")
    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    @Basic
    @Column(name = "BEFORE_BALANCE")
    public Double getBeforeBalance() {
        return beforeBalance;
    }

    public void setBeforeBalance(Double beforeBalance) {
        this.beforeBalance = beforeBalance;
    }

    @Basic
    @Column(name = "PAST_BALANCE")
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
        BalanceHistoryEntity that = (BalanceHistoryEntity) o;
        return balanceId == that.balanceId &&
                Objects.equals(beforeBalance, that.beforeBalance) &&
                Objects.equals(pastBalance, that.pastBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balanceId, beforeBalance, pastBalance);
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
