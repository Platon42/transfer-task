package mercy.digital.transfer.presentation.transaction.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class DoTransfer {

    @JsonProperty("account_no_sender")
    private Integer accountNoSender;

    @JsonProperty("account_no_receiver")
    private Integer accountNoReceiver;

    @JsonProperty("change_currency")
    private String changeCurrency;

    @JsonProperty("amount")
    private Double amount;
}
