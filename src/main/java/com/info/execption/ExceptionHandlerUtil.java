package com.info.execption;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ExceptionHandlerUtil {

    public static ResponseEntity<ErrorDetails> handleException(HttpStatus status, Exception ex,HttpServletRequest request) {
        ErrorDetails errorDetails = createErrorDetails(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(errorDetails);
    }

    private static ErrorDetails createErrorDetails(HttpStatus status, String message, HttpServletRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setStatus(status.value());
        errorDetails.setError(status.getReasonPhrase());
        errorDetails.setMessage(message);
        errorDetails.setPath(request.getRequestURI());
        return errorDetails;
    }

}

