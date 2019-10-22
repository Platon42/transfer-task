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

    @Inject
    private BalanceService balanceService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;

    @Inject
    private ConverterService converterService;

    private CurrencyCode transferCurrency, clientCurrency, beneficiaryCurrency;
    private Integer clientAccountNo, beneficiaryAccountNo;

    private Double calculateTransferAmount(TransferType transferType,
                                           TransactionType transactionType,
                                           Double clientBalance,
                                           Double transferAmount) {

        Double newBalance, exchangeToTransfer, exchangeToBeneficiary;

        switch (transferType) {
            case ALL_PARTICIPANTS_SAME_CURRENCY:
                if (TransactionType.REFILL.equals(transactionType)) {
                    newBalance = clientBalance + transferAmount;
                    return newBalance;
                }
                if (TransactionType.WITHDRAWAL.equals(transactionType)) {
                    newBalance = clientBalance - transferAmount;
                    return newBalance;
                }
                break;
            case ALL_PARTICIPANTS_DIFFERENT_CURRENCY: {
                exchangeToTransfer = converterService.doExchange(transferCurrency, clientCurrency, transferAmount);
                exchangeToBeneficiary = converterService.doExchange(transferCurrency, beneficiaryCurrency, exchangeToTransfer);
                if (TransactionType.REFILL.equals(transactionType)) {
                    newBalance = clientBalance + exchangeToBeneficiary;
                    return newBalance;
                }
                if (TransactionType.WITHDRAWAL.equals(transactionType)) {
                    newBalance = clientBalance - exchangeToTransfer;
                    return newBalance;
                }
                break;
            }
            case SOMEONE_PARTICIPANT_DIFFERENT_CURRENCY:
                exchangeToTransfer = converterService.doExchange(transferCurrency, clientCurrency, transferAmount);
                if (TransactionType.REFILL.equals(transactionType)) {
                    newBalance = clientBalance + exchangeToTransfer;
                    return newBalance;
                }
                if (TransactionType.WITHDRAWAL.equals(transactionType)) {
                    newBalance = clientBalance - exchangeToTransfer;
                    return newBalance;
                }
                break;
        }
        return null;
    }

    private TransactionStatus applyTransferEntities(TransferType transferType,
                                                    TransactionType transactionType,
                                                    ClientAccountEntity clientAccountEntity,
                                                    Double transferAmount,
                                                    Double clientBalance) {


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

        if (clientAccountEntity != null) {
            clientBalance = clientAccountEntity.getBalance();
            clientCurrency = CurrencyCode.valueOf(clientAccountEntity.getCurrency());
        } else {
            log.warn("Not found client by AccountNo " + clientAccountNo);
            return TransactionStatus.NOT_A_CLIENT;
        }

        if (beneficiaryAccountEntity != null) {
            beneficiaryCurrency = CurrencyCode.valueOf(beneficiaryAccountEntity.getCurrency());
        } else {
            log.warn("Not found beneficiary by AccountNo " + beneficiaryAccountNo);
            return TransactionStatus.NOT_A_BENEFICIARY;
        }

        TransactionStatus transactionStatus;

        if (clientCurrency.equals(transferCurrency) & beneficiaryCurrency.equals(transferCurrency)) {

            transactionStatus = applyTransferEntities(TransferType.ALL_PARTICIPANTS_SAME_CURRENCY,
                    TransactionType.WITHDRAWAL, clientAccountEntity, transferAmount, clientBalance);
            if (TransactionStatus.ERROR_OCCURRED.equals(transactionStatus)) return transactionStatus;

            if (beneficiaryAccountEntity.getClient()) {
                //bcs this beneficiary is client
                clientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountNo);
                transactionStatus = applyTransferEntities(TransferType.ALL_PARTICIPANTS_SAME_CURRENCY,
                        TransactionType.REFILL, clientAccountEntity, transferAmount, clientBalance);
                if (TransactionStatus.ERROR_OCCURRED.equals(transactionStatus)) return transactionStatus;
            }

            return transactionStatus;
        }

        if (!clientCurrency.equals(transferCurrency) & !beneficiaryCurrency.equals(transferCurrency) & !clientCurrency.equals(beneficiaryCurrency)) {

            transactionStatus = applyTransferEntities(TransferType.ALL_PARTICIPANTS_DIFFERENT_CURRENCY,
                    TransactionType.WITHDRAWAL, clientAccountEntity, transferAmount, clientBalance);
            if (TransactionStatus.ERROR_OCCURRED.equals(transactionStatus)) return transactionStatus;

            if (beneficiaryAccountEntity.getClient()) {
                clientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountNo);
                transactionStatus = applyTransferEntities(TransferType.ALL_PARTICIPANTS_DIFFERENT_CURRENCY,
                        TransactionType.REFILL, clientAccountEntity, transferAmount, clientBalance);
                if (TransactionStatus.ERROR_OCCURRED.equals(transactionStatus)) return transactionStatus;
            }

            return transactionStatus;

        } else {

            transactionStatus = applyTransferEntities(TransferType.SOMEONE_PARTICIPANT_DIFFERENT_CURRENCY,
                    TransactionType.WITHDRAWAL, clientAccountEntity, transferAmount, clientBalance);
            if (TransactionStatus.ERROR_OCCURRED.equals(transactionStatus)) return transactionStatus;

            if (beneficiaryAccountEntity.getClient()) {
                clientAccountEntity = clientAccountService.findClientEntityAccountByAccountNo(beneficiaryAccountNo);
                transactionStatus = applyTransferEntities(TransferType.SOMEONE_PARTICIPANT_DIFFERENT_CURRENCY,
                        TransactionType.REFILL, clientAccountEntity, transferAmount, clientBalance);
                if (TransactionStatus.ERROR_OCCURRED.equals(transactionStatus)) return transactionStatus;
            }

            return transactionStatus;
        }
    }
}
