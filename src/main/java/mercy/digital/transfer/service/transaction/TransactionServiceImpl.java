package mercy.digital.transfer.service.transaction;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.service.client.account.ClientAccountService;
import mercy.digital.transfer.service.transfer.converter.ConverterService;

@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Inject
    private TransactionService transactionService;

    @Inject
    private ClientAccountService clientAccountService;

    @Inject
    private ConverterService converterService;
    
    public TransactionStatus doTransfer (int sourceId,
                               int targetId,
                               Double reqAmount,
                               TransactionType type,
                               CurrencyCode targetCurrency) {
        Double sourceBalance;
        CurrencyCode currentCurrency;
        ClientAccountEntity clientAccountEntity = clientAccountService.findClientEntityAccountById(sourceId);

        if (clientAccountEntity != null) {
            sourceBalance = clientAccountEntity.getBalance();
            currentCurrency = CurrencyCode.valueOf(clientAccountEntity.getCurrency());
        } else {
            log.info("Not found client by Client Id " + sourceId);
            return TransactionStatus.NOT_A_CLIENT;
        }

        if (currentCurrency.equals(targetCurrency)) {
            if (sourceBalance < reqAmount) return TransactionStatus.INSUFFICIENT_FUNDS;
        }

        if (!currentCurrency.equals(targetCurrency)) {
            Double exchange = this.converterService.doExchange(currentCurrency, targetCurrency, reqAmount);
            if (exchange < reqAmount) return TransactionStatus.INSUFFICIENT_FUNDS;
        }

        return TransactionStatus.INSUFFICIENT_FUNDS;
    }
}
