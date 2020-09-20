package com.modec.vessel_engine.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateEquipment {

    @JsonProperty(required = true)
    @NotNull
    @NotEmpty
    private List<String> equipment;
}
