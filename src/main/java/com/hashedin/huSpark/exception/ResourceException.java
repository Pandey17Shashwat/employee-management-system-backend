package com.hashedin.huSpark.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ResourceException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(ResourceNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceExistsException.class)
    public final ResponseEntity<Object> handleResourceExistsException(ResourceExistsException ex){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.ALREADY_REPORTED);
    }

    public class ExceptionResponse {

        private Date timestamp;
        private String message;


        public ExceptionResponse(Date timestamp, String message) {
            super();
            this.timestamp = timestamp;
            this.message = message;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
