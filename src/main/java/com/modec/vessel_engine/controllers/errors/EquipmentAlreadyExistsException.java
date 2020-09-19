package com.modec.vessel_engine.controllers.errors;

import com.modec.vessel_engine.entities.Equipment;

public class EquipmentAlreadyExistsException extends PersistenceConflictException {
    private final String message;

    public EquipmentAlreadyExistsException(Equipment equipment) {
        this.message = "An equipment already exists with code " + equipment.getCode();
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
