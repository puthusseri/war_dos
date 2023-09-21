package com.tyson.useless.system.exception;

import com.tyson.useless.system.constant.ConfigurationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Base64;

@ControllerAdvice
public class CustomErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OutOfMemoryError.class)
    public ResponseEntity<Object> handleOutOfMemoryError(OutOfMemoryError ex, WebRequest request) {
        String responseData = ConfigurationConstants.SERIALIZED_NUMBER;
        byte[] decodedBytes = Base64.getDecoder().decode(responseData);
        responseData = new String(decodedBytes);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responseData);
    }
}
