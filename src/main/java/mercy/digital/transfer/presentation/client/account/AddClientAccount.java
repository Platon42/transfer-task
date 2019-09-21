package mercy.digital.transfer.presentation.client.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class AddClientAccount {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country_of_issue")
    private String countryOfIssue;
}
