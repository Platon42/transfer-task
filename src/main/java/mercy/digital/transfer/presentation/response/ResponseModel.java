package mercy.digital.transfer.presentation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

@Generated
@JsonPOJOBuilder(withPrefix = "")
@Setter
@Getter
public class ResponseModel {

    @JsonProperty("service_name")
    private String service;

    @JsonProperty("date_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss:SSS")
    private String dateTime;

    @JsonProperty("additional")
    private String additional;

    @JsonProperty("message")
    private String message;

    @JsonProperty("status")
    private Integer status;
}
