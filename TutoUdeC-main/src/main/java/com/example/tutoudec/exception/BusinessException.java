package com.example.tutoudec.exception;

public class BusinessException extends RuntimeException{
    public BusinessException (String message){
        super(message);
    }
}
