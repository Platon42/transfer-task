package mercy.digital.transfer.presentation.client.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import mercy.digital.transfer.presentation.client.GetClient;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class AddClientAccount {

    @JsonProperty("client_param")
    GetClient getClient;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country_of_issue")
    private String countryOfIssue;

    @JsonProperty("account_no")
    private Long accountNo;
}
