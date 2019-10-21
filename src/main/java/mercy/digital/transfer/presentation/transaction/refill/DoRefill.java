package mercy.digital.transfer.presentation.transaction.refill;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class DoRefill {

    @JsonProperty("account_no")
    private Integer accountNo;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("amount")
    private Double amount;
}
