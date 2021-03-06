package com.modec.vessel_engine.controllers.advices;

import com.modec.vessel_engine.controllers.VesselController;
import com.modec.vessel_engine.controllers.errors.InvalidResource;
import com.modec.vessel_engine.controllers.errors.PersistenceConflictException;
import com.modec.vessel_engine.controllers.errors.ResourceDoesNotExist;
import com.modec.vessel_engine.entities.HttpError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(ResourceDoesNotExist.class)
    private HttpError vesselNotFoundHandler(ResourceDoesNotExist ex){
        return new HttpError(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    private HttpError invalidRequestBodyHandler(MethodArgumentNotValidException ex){
        return HttpError.of(ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    private HttpError invalidRequestParamHandler(MissingServletRequestParameterException ex){
        return new HttpError(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler(InvalidResource.class)
    private HttpError invalidResourceHandler(InvalidResource ex){
        return new HttpError(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Throwable.class)
    private HttpError genericErrorHandler(Throwable ex){
        return new HttpError(ex.getMessage());
    }
}
