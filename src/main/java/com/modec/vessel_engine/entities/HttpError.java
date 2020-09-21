package com.modec.vessel_engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
public class HttpError {

    @JsonProperty("error_message")
    private String errorMessage;

    @ApiModelProperty("Error details")
    @JsonProperty
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> reasons;

    public HttpError(String errorMessage){
        this(errorMessage, null);
    }

    public static HttpError of(MethodArgumentNotValidException ex) {
        Map<String, String> reasons = new HashMap<>();
        ex.getBindingResult().getFieldErrors().stream()
                .forEach(error -> reasons.put(error.getField(), error.getDefaultMessage()));
        return new HttpError("Invalid request body", reasons);
    }
}