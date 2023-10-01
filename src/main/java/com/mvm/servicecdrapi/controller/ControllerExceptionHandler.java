package com.mvm.servicecdrapi.controller;

import com.mvm.servicecdrapi.exception.InvalidDataException;
import com.mvm.servicecdrapi.exception.InvalidVinException;
import com.mvm.servicecdrapi.exception.RecordNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    record Error(String code, String message) {
    }

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<Error> handleNotFoundException(Exception exception) {
        return new ResponseEntity<>(new Error(
                "NotFoundException", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Error> handleBadRequestException(Exception exception) {
        return new ResponseEntity<>(new Error(
                "BadRequestException", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidVinException.class, ValidationException.class})
    public ResponseEntity<Error> handleInvalidVinException(Exception exception) {
        return new ResponseEntity<>(new Error(
                "BadRequestException", exception.getCause().getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> internalServerException(Exception exception) {
        return new ResponseEntity<>(new Error(
                "InternalServerException", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
