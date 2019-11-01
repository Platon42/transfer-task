package mercy.digital.transfer.presentation.response;

import mercy.digital.transfer.service.transaction.dict.TransactionStatus;

import java.time.LocalDateTime;

public class ResponseServiceBuilder {

    private ResponseModel responseModel;
    private String service;

    public ResponseServiceBuilder(ResponseModel responseModel, String service) {
        responseModel.setDateTime(LocalDateTime.now());
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
                responseModel.setMessage(transactionStatus.name());
                responseModel.setStatus(0);
                break;
            default:
                break;
        }
        return this;
    }

    public ResponseServiceBuilder build() {
        return new ResponseServiceBuilder(responseModel, service);
    }
}
