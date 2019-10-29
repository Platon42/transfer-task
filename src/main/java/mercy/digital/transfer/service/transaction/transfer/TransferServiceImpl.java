package mercy.digital.transfer.service.transaction.transfer;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.converter.ConverterService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.dict.TransactionType;
import mercy.digital.transfer.service.transaction.dict.TransferType;


@Slf4j
public class TransferServiceImpl implements TransferService {

    private final BalanceService balanceService;
    private final TransactionService transactionService;
    private final ClientAccountService clientAccountService;
    private final BeneficiaryAccountService beneficiaryAccountService;
    private final ConverterService converterService;

    @Inject
    public TransferServiceImpl(BalanceService balanceService,
                               TransactionService transactionService,
                               ClientAccountService clientAccountService,
                               BeneficiaryAccountService beneficiaryAccountService,
                               ConverterService converterService) {
        this.balanceService = balanceService;
        this.transactionService = transactionService;
        this.clientAccountService = clientAccountService;
        this.beneficiaryAccountService = beneficiaryAccountService;
        this.converterService = converterService;
    }

    private CurrencyCode transferCurrency, clientCurrency, beneficiaryCurrency;
    private Integer clientAccountNo, beneficiaryAccountNo;

    private Double calculateBalance(TransactionType transactionType,
                                    Double transferAmount,
                                    Double twiceExchange,
                                    Double clientBalance) {
        double newBalance;
        if (TransactionType.REFILL.equals(transactionType)) {
            if (twiceExchange != null) {
                newBalance = clientBalance + twiceExchange;
            } else {
                newBalance = clientBalance + transferAmount;
            }
            return newBalance;
        }
        if (TransactionType.WITHDRAWAL.equals(transactionType)) {
            newBalance = clientBalance - transferAmount;
            return newBalance;
        }
        return null;
    }


    private Double calculateTransferAmount(TransferType transferType,
                                           TransactionType transactionType,
                                           Double clientBalance,
                                           Double transferAmount) {

        Double exchangeToTransfer;
        Double exchangeToBeneficiary;

        switch (transferType) {
            case ALL_PARTICIPANTS_SAME_CURRENCY:
                System.out.println("ALL_PARTICIPANTS_SAME_CURRENCY");
                return calculateBalance(transactionType, transferAmount, null, clientBalance);
            case ALL_PARTICIPANTS_DIFFERENT_CURRENCY: {
                System.out.println("ALL_PARTICIPANTS_DIFFERENT_CURRENCY");
                exchangeToTransfer = converterService.doExchange(transferCurrency, clientCurrency, transferAmount);
                System.out.println("exchangeToTransfer = " + exchangeToTransfer);
                exchangeToBeneficiary = converterService.doExchange(transferCurrency, beneficiaryCurrency, exchangeToTransfer);
                System.out.println("exchangeToBeneficiary = " + exchangeToBeneficiary);

                return calculateBalance(transactionType, exchangeToTransfer, exchangeToBeneficiary, clientBalance);
            }
            case SOMEONE_PARTICIPANT_DIFFERENT_CURRENCY:
                System.out.println("SOMEONE_PARTICIPANT_DIFFERENT_CURRENCY");
                exchangeToTransfer = converterService.doExchange(transferCurrency, clientCurrency, transferAmount);
                System.out.println("exchangeToTransfer = " + exchangeToTransfer);
                System.out.println("clientBalance = " + clientBalance);

                return calculateBalance(transactionType, exchangeToTransfer, null, clientBalance);
            default: {
                return null;
            }
        }
    }

    private TransactionStatus applyTransferEntities(TransferType transferType,
                                                    TransactionType transactionType,
                                                    ClientAccountEntity clientAccountEntity,
                                                    Double clientBalance,
                                                    Double transferAmount) {


        TransactionEntity transactionEntity = new TransactionEntity();
        BalanceEntity balanceEntity = new BalanceEntity();
        Double newBalance = calculateTransferAmount(
                transferType,
                transactionType,
                clientBalance,
                transferAmount);

        if (newBalance == null) {
            return TransactionStatus.ERROR_OCCURRED;
        }
        if (newBalance < 0.0) {
            return TransactionStatus.INSUFFICIENT_FUNDS;
        }

        transactionService.setTransactionEntity(
                transactionEntity,
                transactionType,
                transferAmount,
                transferCurrency,
                clientAccountNo,
                beneficiaryAccountNo);
        balanceService.setBalanceEntity(clientAccountEntity, balanceEntity, transactionEntity, clientBalance, newBalance);
        return TransactionStatus.TRANSFER_COMPLETED;
    }

    private boolean isEntityNull(ClientAccountEntity clientAccountEntity,
                                 BeneficiaryAccountEntity beneficiaryAccountEntity) {
        if (clientAccountEntity == null) {
            log.warn("Not found client by AccountNo " + clientAccountNo);
            return true;
        }
        if (beneficiaryAccountEntity == null) {
            log.warn("Not found beneficiary by AccountNo " + beneficiaryAccountNo);
            return true;
        }
        return false;
    }

    private boolean doNotContinueTransaction(TransactionStatus status) {
        switch (status) {
            case ERROR_OCCURRED:
            case INCORRECT_AMOUNT:
            case INSUFFICIENT_FUNDS:
                return true;
        }
        return false;
    }

    public TransactionStatus doTransfer(
            int clientAccountNo, //accountNo
            int beneficiaryAccountNo, //accountNo
            Double transferAmount,
            CurrencyCode transferCurrency) {

        this.transferCurrency = transferCurrency;
        this.clientAccountNo = clientAccountNo;
        this.beneficiaryAccountNo = beneficiaryAccountNo;

        Double clientBalance;

        ClientAccountEntity clientAccountEntity =
                clientAccountService.findClientEntityAccountByAccountNo(clientAccountNo);
        BeneficiaryAccountEntity beneficiaryAccountEntity =
                beneficiaryAccountService.findBeneficiaryEntityAccountByAccountNo(beneficiaryAccountNo);
        TransactionStatus transactionStatus;

        if (isEntityNull(clientAccountEntity, beneficiaryAccountEntity)) {
            return TransactionStatus.ERROR_OCCURRED;
        }

        clientBalance = clientAccountEntity.getBalance();
        clientCurrency = CurrencyCode.valueOf(clientAccountEntity.getCurrency());
        beneficiaryCurrency = CurrencyCode.valueOf(beneficiaryAccountEntity.getCurrency());
        boolean isClient = beneficiaryAccountEntity.getClient();
        if (clientCurrency.equals(transferCurrency) && beneficiaryCurrency.equals(transferCurrency)) {

            transactionStatus = applyTransferEntities(
                    TransferType.ALL_PARTICIPANTS_SAME_CURRENCY,
                    TransactionType.WITHDRAWAL, clientAccountEntity, clientBalance, transferAmount);
            if (doNotContinueTransaction(transactionStatus)) return transactionStatus;

            if (isClient) {
                clientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountNo);
                clientBalance = clientAccountEntity.getBalance();
                transactionStatus = applyTransferEntities(
                        TransferType.ALL_PARTICIPANTS_SAME_CURRENCY,
                        TransactionType.REFILL, clientAccountEntity, clientBalance, transferAmount);
            }
            return transactionStatus;
        }

        if (!clientCurrency.equals(transferCurrency) && !beneficiaryCurrency.equals(transferCurrency) & !clientCurrency.equals(beneficiaryCurrency)) {

            transactionStatus = applyTransferEntities(TransferType.ALL_PARTICIPANTS_DIFFERENT_CURRENCY,
                    TransactionType.WITHDRAWAL, clientAccountEntity, clientBalance, transferAmount);
            if (doNotContinueTransaction(transactionStatus)) return transactionStatus;

            if (isClient) {

                clientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountNo);
                clientBalance = clientAccountEntity.getBalance();
                transactionStatus = applyTransferEntities(TransferType.ALL_PARTICIPANTS_DIFFERENT_CURRENCY,
                        TransactionType.REFILL, clientAccountEntity, clientBalance, transferAmount);
            }

            return transactionStatus;

        } else {

            transactionStatus = applyTransferEntities(TransferType.SOMEONE_PARTICIPANT_DIFFERENT_CURRENCY,
                    TransactionType.WITHDRAWAL, clientAccountEntity, clientBalance, transferAmount);
            if (doNotContinueTransaction(transactionStatus)) return transactionStatus;

            System.out.println("Ordinary + " + clientAccountEntity.getBalance());
            System.out.println("Ordinary + " + clientAccountEntity.getCurrency());
            System.out.println("Ordinary + " + clientAccountEntity.getAccountNo());

            if (isClient) {
                clientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountNo);
                System.out.println("isClient + " + clientAccountEntity.getBalance());
                System.out.println("isClient + " + clientAccountEntity.getCurrency());
                System.out.println("isClient + " + clientAccountEntity.getAccountNo());


                clientBalance = clientAccountEntity.getBalance();
                transactionStatus = applyTransferEntities(TransferType.SOMEONE_PARTICIPANT_DIFFERENT_CURRENCY,
                        TransactionType.REFILL, clientAccountEntity, clientBalance, transferAmount);
            }

            return transactionStatus;
        }
    }
}
