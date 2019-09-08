package mercy.digital.transfer.presentation.account;

import lombok.Getter;
import lombok.Value;
import mercy.digital.transfer.domain.BeneficiaryEntity;

import java.time.ZonedDateTime;
import java.util.List;

@Value
@Getter
public class GetAccountDetails {

    private int accountId;
    private Integer accountNo;
    private Double balance;
    private String currency;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String name;
    private String countryOfIssue;
    private List<BeneficiaryEntity> beneficiaryEntities;
}
