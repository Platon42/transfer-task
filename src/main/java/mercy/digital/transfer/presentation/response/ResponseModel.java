package mercy.digital.transfer.presentation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class ResponseModel {

    @JsonProperty("service_name")
    private String service;

    @JsonProperty("date_time")
    private LocalDateTime dateTime;

    @JsonProperty("additional")
    private String additional;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private Integer status;
}
