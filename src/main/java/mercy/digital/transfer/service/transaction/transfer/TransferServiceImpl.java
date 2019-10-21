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
import mercy.digital.transfer.service.transaction.converter.ConverterService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.dict.TransactionType;

@Slf4j
public class TransferServiceImpl implements TransferService {

    @Inject
    private BalanceService balanceService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;

    @Inject
    private ConverterService converterService;

    public TransactionStatus doTransfer (
            int clientAccountNo, //accountNo
            int beneficiaryAccountNo, //accountNo
            Double reqAmount,
            TransactionType type,
            CurrencyCode transferCurrency) {

        Double clientBalance = 0.0;
        CurrencyCode clientCurrency;
        CurrencyCode beneficiaryCurrency;

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
            beneficiaryCurrency = CurrencyCode.valueOf(clientAccountEntity.getCurrency());
        } else {
            log.warn("Not found beneficiary by AccountNo " + beneficiaryAccountNo);
            return TransactionStatus.NOT_A_BENEFICIARY;
        }

        System.out.println(beneficiaryAccountEntity.getClient());
        System.out.println(beneficiaryAccountEntity.getAccountNo());

        System.out.println("clientCurrency " + clientCurrency);
        System.out.println("beneficiaryCurrency " + beneficiaryCurrency);
        System.out.println("transferCurrency " + transferCurrency);


        TransactionEntity transactionEntity = new TransactionEntity();
        BalanceEntity balanceEntity = new BalanceEntity();

        if (clientCurrency.equals(transferCurrency) && beneficiaryCurrency.equals(transferCurrency)) {
            System.out.println("first");
            if (clientBalance < reqAmount) return TransactionStatus.INSUFFICIENT_FUNDS;
            balanceService.transferFunds(
                    clientAccountEntity,
                    transactionEntity,
                    balanceEntity,
                    beneficiaryAccountEntity,
                    clientAccountNo,
                    beneficiaryAccountNo,
                    reqAmount,
                    transferCurrency);
            return TransactionStatus.TRANSFER_COMPLETED;
        }

        if (!clientCurrency.equals(transferCurrency) & beneficiaryCurrency.equals(transferCurrency)) {
            System.out.println("second ");

            Double exchange = converterService.doExchange(transferCurrency, clientCurrency, reqAmount);
            System.out.println("exc " + exchange);
            System.out.println("req " + reqAmount);

            if (exchange < reqAmount) return TransactionStatus.INSUFFICIENT_FUNDS;
            balanceService.transferFunds(
                    clientAccountEntity,
                    transactionEntity,
                    balanceEntity,
                    beneficiaryAccountEntity,
                    clientAccountNo,
                    beneficiaryAccountNo,
                    exchange,
                    transferCurrency);
            return TransactionStatus.TRANSFER_COMPLETED;

        }

        if (!clientCurrency.equals(transferCurrency) & !beneficiaryCurrency.equals(transferCurrency)) {

            System.out.println("third ");
            System.out.println("req " + reqAmount);

            Double exchangeToTransfer = converterService.doExchange(transferCurrency, clientCurrency, reqAmount);
            System.out.println("exchangeToTransfer " + exchangeToTransfer);

            Double exchangeToBeneficiary = converterService.doExchange(clientCurrency, beneficiaryCurrency, exchangeToTransfer);
            System.out.println("exchangeToBeneficiary " + exchangeToBeneficiary);

            if (exchangeToBeneficiary < reqAmount) return TransactionStatus.INSUFFICIENT_FUNDS;
            balanceService.transferFunds(clientAccountEntity,
                    transactionEntity,
                    balanceEntity,
                    beneficiaryAccountEntity,
                    clientAccountNo,
                    beneficiaryAccountNo,
                    exchangeToBeneficiary,
                    transferCurrency);
            return TransactionStatus.TRANSFER_COMPLETED;

        }
        return TransactionStatus.ERROR_OCCURRED;
    }
}
