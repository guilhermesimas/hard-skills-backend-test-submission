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
public class Vessel implements Persistable<String> {

    @ApiModelProperty(value = "Vessel's unique identification code")
    @NotNull
    @NotEmpty
    @JsonProperty(required = true)
    @Id
    private String code;

    @Override
    @JsonIgnore
    public String getId() {
        return code;
    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return true;
    }
}
