package mercy.digital.transfer.presentation.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetClient {

    @JsonProperty("client_id")
    private Integer clientId;

    @JsonProperty("account_no")
    private Long accountNo;

}
