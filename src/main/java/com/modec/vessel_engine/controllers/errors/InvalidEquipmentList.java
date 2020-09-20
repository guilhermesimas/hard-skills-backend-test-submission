package com.modec.vessel_engine.controllers.errors;

import java.util.List;

public class InvalidEquipmentList extends InvalidResource {
    private final String errorMessage;
    public InvalidEquipmentList(List<String> equipmentCodes) {
        this.errorMessage = "Unable to proceed. Equipment with codes " + equipmentCodes.toString() + " do not exist.";
    }

    public String getMessage() {
        return this.errorMessage;
    }
}
