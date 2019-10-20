package mercy.digital.transfer.presentation.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetTransfer {

    @JsonProperty("account_no_sender")
    private Integer accountNoSender;

    @JsonProperty("account_no_receiver")
    private Integer accountNoReceiver;

    @JsonProperty("change_currency")
    private String changeCurrency;

    @JsonProperty("amount")
    private Double amount;
}
