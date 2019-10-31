package mercy.digital.transfer.presentation.beneficiary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetBeneficiary {

    @JsonProperty("beneficiary_id")
    private Integer beneficiaryId;

    @JsonProperty("account_no")
    private Long accountNo;
}
