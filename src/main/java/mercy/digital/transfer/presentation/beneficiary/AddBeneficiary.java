package mercy.digital.transfer.presentation.beneficiary;

import lombok.Setter;
import lombok.Value;
import mercy.digital.transfer.domain.AccountEntity;
import mercy.digital.transfer.domain.BeneficiaryEntity;

@Value
@Setter
public class AddBeneficiary {

    private AccountEntity accountEntity;
    private BeneficiaryEntity beneficiaryEntity;

}
