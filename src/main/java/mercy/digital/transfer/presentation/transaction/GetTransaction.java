package mercy.digital.transfer.presentation.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;
import mercy.digital.transfer.presentation.balance.GetBalance;

import java.util.List;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetTransaction {

    @JsonProperty("source_account_no")
    private Integer sourceAccountNo;

    @JsonProperty("target_account_no")
    private Integer targetAccountNo;

    @JsonProperty("amount")
    private Integer amount;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("balance_list")
    private List<GetBalance> getBalanceList;

}
