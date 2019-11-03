package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import mercy.digital.transfer.utils.db.H2Utils;
import mercy.digital.transfer.utils.prop.Environment;
import mercy.digital.transfer.utils.prop.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.List;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(AccountFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionServiceTest {

    private static TransactionEntity transactionEntity = new TransactionEntity();
    private static Integer TRANSACTION_ID = 1;
    private static Double transactionAmount;
    private static Integer sourceAccountNo;
    private static Integer targetAccountNo;

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    @Inject
    TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionAmount = 100.0;
        sourceAccountNo = 101222;
        targetAccountNo = 122333;
    }

    @Test
    @Order(1)
    void setTransactionEntity() {

        Integer actualTransactionId = transactionService.setTransactionEntity(
                transactionEntity,
                TransactionType.REFILL,
                transactionAmount, CurrencyCode.RUB,
                sourceAccountNo, targetAccountNo);
        Assertions.assertEquals(TRANSACTION_ID, actualTransactionId);
        TransactionService mock = Mockito.mock(TransactionService.class);

    }

    @Order(2)
    @Test
    void findEntitiesTransactionByAccountNo() {
        List<TransactionEntity> entitiesTransactionByAccountNo =
                transactionService.findEntitiesTransactionByAccountNo(sourceAccountNo);
        Assertions.assertEquals(entitiesTransactionByAccountNo.get(0).getTransactionId(), TRANSACTION_ID);
    }

    @Order(3)
    @Test
    void findEntityTransactionById() {
        TransactionEntity entityTransactionById = transactionService.findEntityTransactionById(TRANSACTION_ID);
        Assertions.assertEquals(TRANSACTION_ID, entityTransactionById.getTransactionId());
    }

    @Order(4)
    @Test
    void findAllTransactions() {
        transactionService.setTransactionEntity(
                new TransactionEntity(),
                TransactionType.REFILL,
                transactionAmount, CurrencyCode.RUB,
                sourceAccountNo, targetAccountNo);
        List<TransactionEntity> allTransactions = transactionService.findAllTransactions();
        Assertions.assertEquals(2, allTransactions.get(1).getTransactionId());
    }
}