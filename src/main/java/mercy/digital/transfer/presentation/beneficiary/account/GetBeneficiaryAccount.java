package mercy.digital.transfer.presentation.beneficiary.account;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.ZonedDateTime;

@Getter
@Setter
public class GetBeneficiaryAccount {

    private int accountId;
    private Integer accountNo;

}
