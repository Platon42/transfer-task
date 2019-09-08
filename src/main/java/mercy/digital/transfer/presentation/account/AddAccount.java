package mercy.digital.transfer.presentation.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Setter;
import lombok.Value;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Value
@Setter
public class AddAccount {

    private String currency;
    private String name;
    private String countryOfIssue;
}
