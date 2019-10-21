package mercy.digital.transfer.presentation.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetBalance {

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("transaction_id")
    private Integer transactionId;

    @JsonProperty("before_balance")
    private Integer beforeBalance;

    @JsonProperty("past_balance")
    private Integer pastBalance;
}
