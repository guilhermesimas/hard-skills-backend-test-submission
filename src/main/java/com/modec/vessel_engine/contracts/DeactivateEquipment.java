package com.modec.vessel_engine.contracts;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel("Equipment batch deactivation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeactivateEquipment {

    @ApiModelProperty("List of equipment codes")
    @JsonProperty(required = true)
    @NotNull
    @NotEmpty
    private List<String> equipment;
}
