package mercy.digital.transfer.service.balance;

import mercy.digital.transfer.domain.BalanceEntity;
import mercy.digital.transfer.domain.BeneficiaryAccountEntity;
import mercy.digital.transfer.domain.ClientAccountEntity;
import mercy.digital.transfer.domain.TransactionEntity;
import mercy.digital.transfer.service.transaction.dict.CurrencyCode;

public interface BalanceService {

    void refillClientAccount(ClientAccountEntity clientAccountEntity,
                             TransactionEntity transactionEntity,
                             BalanceEntity balanceEntity,
                             int clientAccountNo,
                             Double amount,
                             Double clientBalance,
                             CurrencyCode chargeCurrency);

    void transferFunds(ClientAccountEntity clientAccountEntity,
                       TransactionEntity transactionEntity,
                       BalanceEntity balanceEntity,
                       BeneficiaryAccountEntity beneficiaryAccountEntity,
                       int clientAccountNo,
                       int beneficiaryAccountNo,
                       Double reqAmount,
                       CurrencyCode targetCurrency);
}
