package com.modec.vessel_engine.controllers.errors;

public abstract class ResourceDoesNotExist extends RuntimeException {
    public abstract String getMessage();
}
