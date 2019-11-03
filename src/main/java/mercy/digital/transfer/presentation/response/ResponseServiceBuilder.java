package mercy.digital.transfer.presentation.response;

import mercy.digital.transfer.service.transaction.dict.TransactionStatus;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


public class ResponseServiceBuilder {

    private ResponseModel responseModel;
    private String service;

    public ResponseServiceBuilder(ResponseModel responseModel, String service) {
        this.responseModel = responseModel;
        this.service = service;
    }

    public ResponseServiceBuilder withMessage(String message) {
        responseModel.setMessage(message);
        return this;
    }

    public ResponseServiceBuilder withStatus(Integer status) {
        responseModel.setStatus(status);
        return this;
    }

    public ResponseServiceBuilder withAdditional(String additional) {
        responseModel.setAdditional(additional);
        return this;
    }

    public ResponseServiceBuilder withTransactionStatus(TransactionStatus transactionStatus) {
        switch (transactionStatus) {
            case INSUFFICIENT_FUNDS:
            case INCORRECT_AMOUNT:
            case ERROR_OCCURRED:
                responseModel.setMessage(transactionStatus.name());
                responseModel.setStatus(-1);
                break;
            case REFILL_COMPLETED:
            case TRANSFER_COMPLETED:
                responseModel.setMessage(transactionStatus.name());
                responseModel.setStatus(0);
                break;
            default:
                break;
        }
        return this;
    }

    public ResponseServiceBuilder build() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Europe/Moscow"));
        responseModel.setDateTime(now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS")));
        responseModel.setService(service);
        return new ResponseServiceBuilder(responseModel, service);
    }
}
