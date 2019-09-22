package mercy.digital.transfer.presentation.beneficiary.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class AddBeneficiaryAccount {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("name")
    private String name;

    @JsonProperty("account_no")
    private String accountNo;
}
