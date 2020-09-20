package com.modec.vessel_engine.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateEquipment {

    @JsonProperty(required = true)
    @NonNull
    private List<String> equipment;
}
