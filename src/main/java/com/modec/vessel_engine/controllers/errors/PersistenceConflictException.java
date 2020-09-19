package com.modec.vessel_engine.controllers.errors;

public abstract class PersistenceConflictException extends RuntimeException  {
    public abstract String getMessage();
}
