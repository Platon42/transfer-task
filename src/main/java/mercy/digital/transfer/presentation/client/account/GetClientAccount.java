package mercy.digital.transfer.presentation.client.account;

import lombok.Getter;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Getter
public class GetClientAccount {

    private int accountId;
    private Integer accountNo;
    private Double balance;
    private String currency;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String name;
    private String countryOfIssue;

}
