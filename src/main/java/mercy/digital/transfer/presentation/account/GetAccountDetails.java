package mercy.digital.transfer.presentation.account;

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
public class GetAccountDetails {

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

    @JsonProperty("name")
    private String countryOfIssue;

    @JsonProperty("beneficiary_list")
    private List<BeneficiaryEntity> beneficiaryEntities;
}
