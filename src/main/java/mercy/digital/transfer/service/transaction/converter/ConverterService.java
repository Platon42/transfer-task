package mercy.digital.transfer.service.transaction.converter;

import mercy.digital.transfer.service.transaction.CurrencyCode;

public interface ConverterService {
    Double doExchange (CurrencyCode from, CurrencyCode to, Double amount);
}
