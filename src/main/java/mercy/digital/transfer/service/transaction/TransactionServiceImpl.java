package mercy.digital.transfer.service.transaction;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.beneficiary.account.BeneficiaryAccountService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.converter.ConverterService;

@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Inject
    private BalanceService balanceService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private BeneficiaryAccountService beneficiaryAccountService;

    @Inject
    private ConverterService converterService;

    public TransactionStatus doTransfer(
            int clientAccountNo, //accountNo
            int beneficiaryAccountNo, //accountNo
            Double reqAmount,
            TransactionType type,
            CurrencyCode transferCurrency) {

        Double clientBalance;
        CurrencyCode clientCurrency;
        CurrencyCode beneficiaryCurrency;

        ClientAccountEntity clientAccountEntity =
                clientAccountService.findClientEntityAccountByAccountNo(clientAccountNo);
        BeneficiaryAccountEntity beneficiaryAccountEntity =
                beneficiaryAccountService.findBeneficiaryEntityAccountByAccountNo(beneficiaryAccountNo);

        BalanceEntity balanceEntity = new BalanceEntity();

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

        if (clientCurrency.equals(transferCurrency) & clientCurrency.equals(beneficiaryCurrency)) {
            if (clientBalance < reqAmount) return TransactionStatus.INSUFFICIENT_FUNDS;
        }

        if (!clientCurrency.equals(transferCurrency)) {
            Double exchange = this.converterService.doExchange(clientCurrency, transferCurrency, reqAmount);
            balanceEntity.setClientAccountByAccountId(clientAccountEntity);
            balanceEntity.setPastBalance(reqAmount);
            balanceService.updateClientBalance(balanceEntity);

            if (exchange < reqAmount) return TransactionStatus.INSUFFICIENT_FUNDS;
        }

        return TransactionStatus.INSUFFICIENT_FUNDS;
    }
}