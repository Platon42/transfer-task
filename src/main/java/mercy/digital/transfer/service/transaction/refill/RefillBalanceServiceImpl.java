package mercy.digital.transfer.service.transaction.refill;

import com.google.inject.Inject;
import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.balance.BalanceService;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transaction.TransactionService;
import mercy.digital.transfer.service.transaction.converter.ConverterService;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;
import mercy.digital.transfer.service.transaction.dict.TransactionStatus;
import mercy.digital.transfer.service.transaction.dict.TransactionType;

public class RefillBalanceServiceImpl implements RefillBalanceService {

    private final BalanceService balanceService;
    private final TransactionService transactionService;
    private final ClientAccountService clientAccountService;
    private final ConverterService converterService;

    @Inject
    public RefillBalanceServiceImpl(BalanceService balanceService,
                                    TransactionService transactionService,
                                    ClientAccountService clientAccountService,
                                    ConverterService converterService) {
        this.balanceService = balanceService;
        this.transactionService = transactionService;
        this.clientAccountService = clientAccountService;
        this.converterService = converterService;
    }

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
        Double newBalance;
        if (clientCurrency.equals(chargeCurrency)) {
            newBalance = clientBalance + transactionAmount;
            transactionService.setTransactionEntity(
                    transactionEntity,
                    TransactionType.REFILL,
                    transactionAmount,
                    chargeCurrency,
                    null,
                    clientAccountNo);
            balanceService.setBalanceEntity(clientAccountEntity, balanceEntity, transactionEntity, clientBalance, newBalance);
            return TransactionStatus.REFILL_COMPLETED;
        } else {
            Double exchangeAmount = converterService.doExchange(chargeCurrency, clientCurrency, transactionAmount);
            newBalance = clientBalance + exchangeAmount;
            transactionService.setTransactionEntity(
                    transactionEntity,
                    TransactionType.REFILL,
                    transactionAmount,
                    chargeCurrency,
                    null,
                    clientAccountNo);
            balanceService.setBalanceEntity(clientAccountEntity, balanceEntity, transactionEntity, clientBalance, newBalance);

            return TransactionStatus.REFILL_COMPLETED;

        }
    }
}
