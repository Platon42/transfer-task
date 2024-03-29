package mercy.digital.transfer.presentation.beneficiary.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import mercy.digital.transfer.presentation.beneficiary.GetBeneficiary;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class AddBeneficiaryAccount {

    @JsonProperty("beneficiary_param")
    GetBeneficiary getBeneficiary;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("account_name")
    private String accountName;

    @JsonProperty("account_no")
    private String accountNo;
}
