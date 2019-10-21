package mercy.digital.transfer.service.transaction.refill;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.converter.ConverterService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;

public class RefillBalanceServiceImpl implements RefillBalanceService {

    @Inject
    private BalanceService balanceService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private ConverterService converterService;

    public TransactionStatus refillBalance(int clientAccountNo,
                                           Double transactionAmount,
                                           CurrencyCode chargeCurrency) {

        if (transactionAmount <= 0) return TransactionStatus.INCORRECT_AMOUNT;

        ClientAccountEntity clientAccountEntity =
                clientAccountService.findClientEntityAccountByAccountNo(clientAccountNo);
        TransactionEntity transactionEntity = new TransactionEntity();
        BalanceEntity balanceEntity = new BalanceEntity();

        Double clientBalance;
        CurrencyCode clientCurrency;

        if (clientAccountEntity.getBalance() == null) {
            clientBalance = 0.0;
        } else {
            clientBalance = clientAccountEntity.getBalance();
        }

        clientCurrency = CurrencyCode.valueOf(clientAccountEntity.getCurrency());

        if (clientCurrency.equals(chargeCurrency)) {
            balanceService.refillClientAccount(clientAccountEntity, transactionEntity, balanceEntity,
                    clientAccountNo, transactionAmount, transactionAmount, clientBalance, clientCurrency);
            return TransactionStatus.REFILL_COMPLETED;
        } else {
            Double refillAmount = converterService.doExchange(chargeCurrency, clientCurrency, transactionAmount);
            balanceService.refillClientAccount(clientAccountEntity, transactionEntity, balanceEntity,
                    clientAccountNo, transactionAmount, refillAmount, clientBalance, chargeCurrency);
            return TransactionStatus.REFILL_COMPLETED;

        }

    }
}
