package com.mvm.servicecdrapi.exception;

public class InvalidVinException extends RuntimeException {
    public InvalidVinException(String message) {
        super(message);
    }
}
