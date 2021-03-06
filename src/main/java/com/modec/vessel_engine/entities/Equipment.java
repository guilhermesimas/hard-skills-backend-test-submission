package com.modec.vessel_engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@JsonDeserialize
@JsonSerialize
@Entity
@Table
public class Equipment {

    @ApiModelProperty("Equipment's unique identification code")
    @NotNull
    @NotEmpty
    @JsonProperty(required = true)
    @Id
    private String code;

    @ApiModelProperty("Equipment's name")
    @NotNull
    @NotEmpty
    @JsonProperty(required = true)
    private String name;

    @ApiModelProperty("Equipment's location")
    @NotNull
    @NotEmpty
    @JsonProperty(required = true)
    private String location;

    @ApiModelProperty("Equipment's status")
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, defaultValue = "active")
    private String status = "active";

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "vesselCode", referencedColumnName = "code")
    private Vessel vessel;
}
