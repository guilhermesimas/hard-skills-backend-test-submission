package com.modec.vessel_engine.controllers.errors;

import com.modec.vessel_engine.entities.Vessel;

public class VesselAlreadyExistsException extends PersistenceConflictException {
    private final String message;

    public VesselAlreadyExistsException(Vessel vessel) {
        this.message = "A vessel already exists with the code " + vessel.getCode();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
