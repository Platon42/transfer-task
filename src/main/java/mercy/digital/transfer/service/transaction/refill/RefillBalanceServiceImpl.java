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

    private TransactionStatus setRefillEntities(Double clientBalance,
                                                Double transactionAmount,
                                                TransactionEntity transactionEntity,
                                                BalanceEntity balanceEntity,
                                                ClientAccountEntity clientAccountEntity,
                                                CurrencyCode chargeCurrency,
                                                Integer clientAccountNo) {
        Double newBalance = clientBalance + transactionAmount;
        Integer id;
        id = transactionService.setTransactionEntity(
                transactionEntity,
                TransactionType.REFILL,
                transactionAmount,
                chargeCurrency,
                null,
                clientAccountNo);
        if (id == null) return TransactionStatus.ERROR_OCCURRED;

        id = balanceService.setBalanceEntity(clientAccountEntity, balanceEntity, transactionEntity, clientBalance, newBalance);
        if (id == null) return TransactionStatus.ERROR_OCCURRED;
        return TransactionStatus.REFILL_COMPLETED;
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

        if (clientCurrency.equals(chargeCurrency)) {
            return setRefillEntities(clientBalance,
                    transactionAmount, transactionEntity, balanceEntity,
                    clientAccountEntity, chargeCurrency, clientAccountNo);
        } else {
            Double exchangeAmount = converterService.doExchange(chargeCurrency, clientCurrency, transactionAmount);
            return setRefillEntities(clientBalance,
                    exchangeAmount, transactionEntity, balanceEntity,
                    clientAccountEntity, chargeCurrency, clientAccountNo);
        }
    }
}
