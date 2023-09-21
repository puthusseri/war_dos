package com.tyson.useless.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomErrorController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OutOfMemoryError.class)
    public ResponseEntity<Object> handleOutOfMemoryError(OutOfMemoryError ex, WebRequest request) {
        // TDOO : store this data as base64 encoded content and send it after decoding. (To avoid grep of flag)
        String responseData = "zctf2023{thisIsMycusTomvalueFroTyson}";
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(responseData);
    }
}
