package mercy.digital.transfer.presentation.transaction;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetTransactionDetails {

    @JsonProperty("account_no")
    private Integer accountNo;

    @JsonProperty("transaction_list")
    private List<GetTransaction> transactionList;

}
