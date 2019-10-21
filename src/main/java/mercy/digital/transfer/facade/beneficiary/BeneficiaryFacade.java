package mercy.digital.transfer.facade.beneficiary;

import mercy.digital.transfer.presentation.beneficiary.AddBeneficiary;
import mercy.digital.transfer.presentation.beneficiary.account.AddBeneficiaryAccount;
import mercy.digital.transfer.presentation.response.ResponseModel;

public interface BeneficiaryFacade {
    ResponseModel addBeneficiary(AddBeneficiary beneficiary);
    ResponseModel addBeneficiaryAccount(AddBeneficiaryAccount beneficiaryAccount);
}
