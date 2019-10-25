package mercy.digital.transfer.presentation.beneficiary.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.time.LocalDateTime;
import java.util.List;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class GetBeneficiaryAccountDetails {

    private int accountId;

    @JsonProperty("account_no")
    private Integer accountNo;

    @JsonProperty("balance")
    private Double balance;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("name")
    private String name;

    @JsonProperty("name")
    private String countryOfIssue;

    @JsonProperty("beneficiary_list")
    private List<BeneficiaryEntity> beneficiaryEntities;
}
