package com.mvm.servicecdrapi.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String message){
        super(message);
    }
}
