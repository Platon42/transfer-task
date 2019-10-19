package mercy.digital.transfer.presentation.client.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.time.ZonedDateTime;
import java.util.List;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetClientAccountDetails {

    private int accountId;

    @JsonProperty("account_no")
    private Integer accountNo;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("country_of_issue")
    private String countryOfIssue;

    @JsonProperty("beneficiary_list")
    private List<BeneficiaryEntity> beneficiaryEntities;
}
