package mercy.digital.transfer.service.transaction;

import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


class TransactionServiceTest {

    @BeforeEach
    void setUp() {
        TransactionEntity transactionEntity;
        TransactionType transactionType;
        Double transactionAmount;
        CurrencyCode currencyCode;
        Integer sourceAccountNo;
        Integer targetAccountNo;
    }

    @Order(1)
    @Test
    void addEntityTransaction() {

    }

    @Order(2)
    @Test
    void findEntitiesTransactionByAccountNo() {

    }


    @Test
    void setTransactionEntity() {

    }

    @Test
    void findEntityTransactionById() {
    }

    @Test
    void findAllTransactions() {
    }


}