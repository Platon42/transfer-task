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

    public TransactionStatus refillBalance(int clientAccountNo, Double amount, CurrencyCode chargeCurrency) {

        ClientAccountEntity clientAccountEntity =
                clientAccountService.findClientEntityAccountByAccountNo(clientAccountNo);
        TransactionEntity transactionEntity = new TransactionEntity();
        BalanceEntity balanceEntity = new BalanceEntity();

        Double clientBalance = 0.0;
        CurrencyCode clientCurrency;

        if (clientAccountEntity.getBalance() == null) clientBalance = 0.0;
        clientCurrency = CurrencyCode.valueOf(clientAccountEntity.getCurrency());

        if (amount <= 0) return TransactionStatus.INCORRECT_AMOUNT;

        if (chargeCurrency.equals(clientCurrency)) {
            balanceService.refillClientAccount(clientAccountEntity, transactionEntity, balanceEntity,
                    clientAccountNo, amount, clientBalance, clientCurrency);
            return TransactionStatus.REFILL_COMPLETED;
        } else {
            Double exchange = this.converterService.doExchange(clientCurrency, chargeCurrency, amount);
            balanceService.refillClientAccount(clientAccountEntity, transactionEntity, balanceEntity,
                    clientAccountNo, exchange, clientBalance, chargeCurrency);
            return TransactionStatus.REFILL_COMPLETED;

        }

    }
}
