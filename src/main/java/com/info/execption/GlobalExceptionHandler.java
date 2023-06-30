package com.info.execption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorDetails errorDetails = createErrorDetails(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(errorDetails);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorDetails errorDetails = createErrorDetails(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(errorDetails);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDetails errorDetails = createErrorDetails(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(errorDetails);
    }

    private ErrorDetails createErrorDetails(HttpStatus status, String message, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(LocalDateTime.now());
        errorDetails.setStatus(status.value());
        errorDetails.setError(status.getReasonPhrase());
        errorDetails.setMessage(message);
        errorDetails.setPath(request.getDescription(false));
        return errorDetails;
    }
}

