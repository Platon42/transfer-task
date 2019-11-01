package mercy.digital.transfer.unit;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.*;
import mercy.digital.transfer.module.AccountFacadeModule;
import mercy.digital.transfer.module.BeneficiaryFacadeModule;
import mercy.digital.transfer.module.ClientFacadeModule;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.beneficiary.BeneficiaryService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
import mercy.digital.transfer.service.client.ClientService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import mercy.digital.transfer.service.transaction.transfer.TransferService;
import mercy.digital.transfer.utils.Environment;
import mercy.digital.transfer.utils.H2Utils;
import mercy.digital.transfer.utils.PropUtils;
import name.falgout.jeffrey.testing.junit5.GuiceExtension;
import name.falgout.jeffrey.testing.junit5.IncludeModule;
import org.apache.commons.lang3.Range;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@ExtendWith(GuiceExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@IncludeModule(AccountFacadeModule.class)
@IncludeModule(BeneficiaryFacadeModule.class)
@IncludeModule(ClientFacadeModule.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class TransferServiceTest {

    static {
        PropUtils.setProperties(Environment.TEST);
        H2Utils.startDb(Environment.TEST);
    }

    private ClientEntity clientEntityStub;
    private ClientAccountEntity clientAccountEntityStub;
    private BeneficiaryAccountEntity beneficiaryAccountEntityStub;
    private BeneficiaryEntity beneficiaryEntity;

    private BalanceEntity balanceEntity;
    private TransactionEntity transactionEntity;

    private ClientEntity clientEntityStubTwo;
    private ClientAccountEntity clientAccountEntityStubTwo;
    private BalanceEntity balanceEntityTwo;
    private TransactionEntity transactionEntityTwo;

    @Inject
    private BalanceService balanceService;

    @Inject
    private ClientService clientService;

    @Inject
    private BeneficiaryService beneficiaryService;

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private TransferService transferService;

    void setUpClientFirst(CurrencyCode currencyCode, Integer accountNo) {

        clientEntityStub = new ClientEntity();
        clientEntityStub.setFirstName("Ivan");
        clientEntityStub.setLastName("Smirnov");
        clientEntityStub.setResidentCountry("RU");
        clientEntityStub.setSex(1);
        clientEntityStub.setResidentCountry("Russia");
        clientEntityStub.setMiddleName("Vlad");
        clientEntityStub.setBirthday(new Date(19082019));

        clientAccountEntityStub = new ClientAccountEntity();
        clientAccountEntityStub.setCurrency(currencyCode.name());
        clientAccountEntityStub.setAccountNo(accountNo);
        clientAccountEntityStub.setCreatedAt(Timestamp.from(Instant.now()));
        clientAccountEntityStub.setClientByClientId(clientEntityStub);

        Integer actualId = clientService.addEntityClient(clientEntityStub);
        Integer actualAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStub);
    }

    void setUpClientSecond(CurrencyCode currencyCode, Integer accountNo) {
        clientEntityStubTwo = new ClientEntity();
        clientEntityStubTwo.setFirstName("Nick");
        clientEntityStubTwo.setLastName("Smirnov");
        clientEntityStubTwo.setResidentCountry("RU");
        clientEntityStubTwo.setSex(1);
        clientEntityStubTwo.setResidentCountry("USA");
        clientEntityStubTwo.setMiddleName("Gut");
        clientEntityStubTwo.setBirthday(new Date(19082019));

        clientAccountEntityStubTwo = new ClientAccountEntity();
        clientAccountEntityStubTwo.setCurrency(currencyCode.name());
        clientAccountEntityStubTwo.setAccountNo(accountNo);
        clientAccountEntityStubTwo.setCreatedAt(Timestamp.from(Instant.now()));
        clientAccountEntityStubTwo.setClientByClientId(clientEntityStubTwo);

        Integer actualId = clientService.addEntityClient(clientEntityStubTwo);

        Integer actualAccountId = clientAccountService.addClientEntityAccount(clientAccountEntityStubTwo);

    }

    void setUpBeneficiary(CurrencyCode currencyCode, Integer accountNo) {

        beneficiaryEntity = new BeneficiaryEntity();
        beneficiaryEntity.setCity("London");
        beneficiaryEntity.setPostcode("18900");

        beneficiaryAccountEntityStub = new BeneficiaryAccountEntity();
        beneficiaryAccountEntityStub.setAccountNo(accountNo);
        beneficiaryAccountEntityStub.setCurrency(currencyCode.name());

        Integer beneficiaryId = beneficiaryService.addEntityBeneficiary(beneficiaryEntity);
        Integer beneficiaryAccountId = beneficiaryAccountService.addBeneficiaryEntityAccount(beneficiaryAccountEntityStub);
    }

    void refillFirstBalance(CurrencyCode currencyCode, Double amount, Integer accountNo) {

        balanceEntity = new BalanceEntity();
        transactionEntity = new TransactionEntity();
        Integer actualTransactionId = transactionService.setTransactionEntity(
                transactionEntity,
                TransactionType.REFILL,
                amount, currencyCode,
                null, accountNo);

        Integer balanceId = balanceService.setBalanceEntity(
                clientAccountEntityStub,
                balanceEntity,
                transactionEntity, amount, amount);

        Assertions.assertEquals(amount, balanceEntity.getBeforeBalance());
        Assertions.assertEquals(amount, balanceEntity.getPastBalance());
    }

    void refillSecondBalance(CurrencyCode currencyCode, Double amount, Integer accountNo) {

        balanceEntityTwo = new BalanceEntity();
        transactionEntityTwo = new TransactionEntity();

        Integer actualTransactionId = transactionService.setTransactionEntity(
                transactionEntityTwo,
                TransactionType.REFILL,
                amount, currencyCode,
                null, accountNo);

        Integer balanceId = balanceService.setBalanceEntity(
                clientAccountEntityStubTwo,
                balanceEntityTwo,
                transactionEntityTwo, amount, amount);

        Assertions.assertEquals(amount, balanceEntityTwo.getBeforeBalance());
        Assertions.assertEquals(amount, balanceEntityTwo.getPastBalance());
    }

    @Test
    @Order(1)
    void doTransferSameCurrencyBetweenClients() {
        Integer senderAccountNo = 10122;
        Integer receiverAccountNo = 10123;

        setUpClientFirst(CurrencyCode.RUB, senderAccountNo);
        setUpClientSecond(CurrencyCode.RUB, receiverAccountNo);
        setUpBeneficiary(CurrencyCode.RUB, receiverAccountNo);

        refillFirstBalance(CurrencyCode.RUB, 150.0, senderAccountNo);
        refillSecondBalance(CurrencyCode.RUB, 100.0, receiverAccountNo);

        TransactionStatus transactionStatus =
                transferService.doTransfer(senderAccountNo, receiverAccountNo, 40.0, CurrencyCode.RUB);
        Assertions.assertEquals(TransactionStatus.TRANSFER_COMPLETED, transactionStatus);

        Double balanceSender = clientAccountService.findClientEntityAccountByAccountNo(senderAccountNo).getBalance();
        Double balanceReceiver = clientAccountService.findClientEntityAccountByAccountNo(receiverAccountNo).getBalance();

        Assertions.assertEquals(110, balanceSender);
        Assertions.assertEquals(140, balanceReceiver);

    }

    @Test
    @Order(2)
    void doTransferSomeDiffCurrencyBetweenClients() {
        Integer senderAccountNo = 10125;
        Integer receiverAccountNo = 10126;

        setUpClientFirst(CurrencyCode.USD, senderAccountNo);
        refillFirstBalance(CurrencyCode.USD, 150.0, senderAccountNo);

        setUpClientSecond(CurrencyCode.RUB, receiverAccountNo);
        setUpBeneficiary(CurrencyCode.RUB, receiverAccountNo);
        refillSecondBalance(CurrencyCode.RUB, 100.0, receiverAccountNo);

        TransactionStatus transactionStatus =
                transferService.doTransfer(senderAccountNo, receiverAccountNo, 40.0, CurrencyCode.USD);
        Assertions.assertEquals(TransactionStatus.TRANSFER_COMPLETED, transactionStatus);

        Double balanceSender = clientAccountService.findClientEntityAccountByAccountNo(senderAccountNo).getBalance();
        Assertions.assertEquals(110, balanceSender);
        Double balanceReceiver = clientAccountService.findClientEntityAccountByAccountNo(receiverAccountNo).getBalance();
        Range<Double> receiverRange = Range.between(2500.0, 2800.00);
        Assertions.assertTrue(receiverRange.contains(balanceReceiver));
    }

    @Test
    @Order(3)
    void doTransferSomeDiffCurrencyBetweenClients_2() {
        Integer senderAccountNo = 10127;
        Integer receiverAccountNo = 10128;

        setUpClientFirst(CurrencyCode.RUB, senderAccountNo);
        refillFirstBalance(CurrencyCode.RUB, 11150.0, senderAccountNo);

        setUpClientSecond(CurrencyCode.USD, receiverAccountNo);
        setUpBeneficiary(CurrencyCode.USD, receiverAccountNo);
        refillSecondBalance(CurrencyCode.USD, 100.0, receiverAccountNo);

        TransactionStatus transactionStatus =
                transferService.doTransfer(senderAccountNo, receiverAccountNo, 40.0, CurrencyCode.USD);
        Assertions.assertEquals(TransactionStatus.TRANSFER_COMPLETED, transactionStatus);

        Double balanceReceiver = clientAccountService.findClientEntityAccountByAccountNo(receiverAccountNo).getBalance();
        Assertions.assertEquals(140, balanceReceiver);

        Double balanceSender = clientAccountService.findClientEntityAccountByAccountNo(senderAccountNo).getBalance();
        Range<Double> senderRange = Range.between(7850.0, 8700.00);
        Assertions.assertTrue(senderRange.contains(balanceSender));

    }

    @Test
    @Order(4)
    void doTransferSomeDiffCurrencyBetweenClients_3() {
        Integer senderAccountNo = 10129;
        Integer receiverAccountNo = 10130;

        setUpClientFirst(CurrencyCode.USD, senderAccountNo);
        refillFirstBalance(CurrencyCode.USD, 100.0, senderAccountNo);

        setUpClientSecond(CurrencyCode.USD, receiverAccountNo);
        setUpBeneficiary(CurrencyCode.USD, receiverAccountNo);
        refillSecondBalance(CurrencyCode.USD, 100.0, receiverAccountNo);

        TransactionStatus transactionStatus =
                transferService.doTransfer(senderAccountNo, receiverAccountNo, 1000.0, CurrencyCode.RUB);
        Assertions.assertEquals(TransactionStatus.TRANSFER_COMPLETED, transactionStatus);

        Double balanceReceiver = clientAccountService.findClientEntityAccountByAccountNo(receiverAccountNo).getBalance();
        Double balanceSender = clientAccountService.findClientEntityAccountByAccountNo(senderAccountNo).getBalance();
        Range<Double> receiverRange = Range.between(105.00, 120.00);
        Range<Double> senderRange = Range.between(80.0, 90.00);

        Assertions.assertTrue(receiverRange.contains(balanceReceiver));
        Assertions.assertTrue(senderRange.contains(balanceSender));

    }

    @Test
    @Order(5)
    void doTransferAllDiffCurrencyBetweenClients() {
        Integer senderAccountNo = 10131;
        Integer receiverAccountNo = 10132;

        setUpClientFirst(CurrencyCode.USD, senderAccountNo);
        refillFirstBalance(CurrencyCode.USD, 100.0, senderAccountNo);

        setUpClientSecond(CurrencyCode.RUB, receiverAccountNo);
        setUpBeneficiary(CurrencyCode.RUB, receiverAccountNo);
        refillSecondBalance(CurrencyCode.RUB, 100.0, receiverAccountNo);

        TransactionStatus transactionStatus =
                transferService.doTransfer(senderAccountNo, receiverAccountNo, 50.0, CurrencyCode.EUR);
        Assertions.assertEquals(TransactionStatus.TRANSFER_COMPLETED, transactionStatus);

        Double balanceReceiver = clientAccountService.findClientEntityAccountByAccountNo(receiverAccountNo).getBalance();
        Double balanceSender = clientAccountService.findClientEntityAccountByAccountNo(senderAccountNo).getBalance();

        Range<Double> receiverRange = Range.between(3900.00, 4150.00);
        Range<Double> senderRange = Range.between(40.0, 50.00);

        Assertions.assertTrue(receiverRange.contains(balanceReceiver));
        Assertions.assertTrue(senderRange.contains(balanceSender));
    }

}