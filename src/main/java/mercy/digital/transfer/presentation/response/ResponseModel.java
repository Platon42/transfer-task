package mercy.digital.transfer.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class ResponseModel {

    @JsonProperty("response_service")
    private String service;

    @JsonProperty("entity_id")
    private int id;

    @JsonProperty("date_time")
    private ZonedDateTime dateTime;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("message")
    private String message;

}
