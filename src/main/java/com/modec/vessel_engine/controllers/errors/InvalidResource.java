package com.modec.vessel_engine.controllers.errors;

public abstract class InvalidResource extends RuntimeException {
    public abstract String getMessage();
}
