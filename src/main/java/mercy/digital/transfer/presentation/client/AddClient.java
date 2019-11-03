package mercy.digital.transfer.presentation.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class AddClient {

    @JsonProperty(value = "first_name", required = true)
    private String firstName;

    @JsonProperty(value = "last_name", required = true)
    private String lastName;

    @JsonProperty("middle_name")
    private String middleName;

    @JsonProperty(value = "birthday", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;

    @JsonProperty(value = "sex", required = true)
    private int sex;

    @JsonProperty(value = "resident_country", required = true)
    private String residentCountry;
}
