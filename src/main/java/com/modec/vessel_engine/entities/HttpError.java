package com.modec.vessel_engine.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class HttpError {

    @JsonProperty("error_message")
    private String errorMessage;
}