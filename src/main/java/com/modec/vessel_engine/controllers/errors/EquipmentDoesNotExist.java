package com.modec.vessel_engine.controllers.errors;

public class EquipmentDoesNotExist extends ResourceDoesNotExist {
    private final String errorMessage;
    public EquipmentDoesNotExist(String equipmentCode) {
        this.errorMessage = "Equipment with code " + equipmentCode + " does not exist.";
    }

    public String getMessage() {
        return this.errorMessage;
    }
}
