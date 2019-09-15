package mercy.digital.transfer.presentation.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class AddClient {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty("birthday")
    private Date birthday;

    @JsonProperty("sex")
    private int sex;

    @JsonProperty("resident_country")
    private String residentCountry;
}
