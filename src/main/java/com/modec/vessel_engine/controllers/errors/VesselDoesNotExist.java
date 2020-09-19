package com.modec.vessel_engine.controllers.errors;

import com.modec.vessel_engine.entities.Vessel;

import javax.validation.Valid;

public class VesselDoesNotExist extends RuntimeException {
    private final String errorMessage;
    public VesselDoesNotExist(String vesselCode) {
        this.errorMessage = "Vessel with code " + vesselCode + " does not exist.";
    }

    public String getMessage() {
        return this.errorMessage;
    }
}
