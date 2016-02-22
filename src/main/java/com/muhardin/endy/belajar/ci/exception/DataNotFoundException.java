package com.muhardin.endy.belajar.ci.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    
    public DataNotFoundException(String message) {
        super(message);
    }
    
}
