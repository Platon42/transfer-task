package mercy.digital.transfer.service.transaction;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(AccountFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceTest {
    private static TransactionEntity transactionEntity = new TransactionEntity();
    private static Double transactionAmount = 100.0;

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    TransactionService transactionService;
    Integer sourceAccountNo;
    Integer targetAccountNo;

    @BeforeEach
    void setUp() {
        TransactionType transactionType;
        Double transactionAmount;
        CurrencyCode currencyCode;
        Integer sourceAccountNo;
        Integer targetAccountNo;
    }

    @Order(1)
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