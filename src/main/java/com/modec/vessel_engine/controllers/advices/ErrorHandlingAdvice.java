package com.modec.vessel_engine.controllers.advices;

import com.modec.vessel_engine.controllers.VesselController;
import com.modec.vessel_engine.controllers.errors.PersistenceConflictException;
import com.modec.vessel_engine.entities.HttpError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice(basePackageClasses = {VesselController.class})
public class ErrorHandlingAdvice {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(PersistenceConflictException.class)
    private HttpError persistenceConflictError(PersistenceConflictException ex){
        return new HttpError(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    private HttpError genericErrorHandler(Throwable ex){
        return new HttpError(ex.getMessage());
    }
}