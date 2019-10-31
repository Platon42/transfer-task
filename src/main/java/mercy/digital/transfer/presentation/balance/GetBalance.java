package mercy.digital.transfer.presentation.balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetBalance {

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("transaction_id")
    private Integer transactionId;

    @JsonProperty("before_balance")
    private Double beforeBalance;

    @JsonProperty("past_balance")
    private Double pastBalance;
}
