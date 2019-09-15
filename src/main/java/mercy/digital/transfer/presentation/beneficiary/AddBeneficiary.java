package mercy.digital.transfer.presentation.beneficiary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class AddBeneficiary {

    @JsonProperty("account_id")
    private int accountId;

    @JsonProperty("street_line")
    private String streetLine;

    @JsonProperty("city")
    private String city;

    @JsonProperty("country")
    private String country;

    @JsonProperty("postcode")
    private String postcode;

}
