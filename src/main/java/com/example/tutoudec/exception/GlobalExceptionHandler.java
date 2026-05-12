package com.example.tutoudec.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class
GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlerNotFound(ResourceNotFoundException ex){
        return buildResponse (HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handlerBusiness(BusinessException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, Object>> handlerDuplicate(DuplicateResourceException ex){
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>>handlerGeneral(Exception ex){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,"ERROR INTERNO DEL SERVIDOR");
    }
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }


}
