package mercy.digital.transfer.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class ResponseModel {

    @JsonProperty("service")
    private String service;

    @JsonProperty("id")
    private int id;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("message")
    private String message;

}
