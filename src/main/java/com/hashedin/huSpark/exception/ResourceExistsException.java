package com.hashedin.huSpark.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class ResourceExistsException extends RuntimeException{
    public ResourceExistsException(String message){super(message);}
}
