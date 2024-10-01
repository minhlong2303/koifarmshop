package com.example.koifarm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //danh dau day la 1 class de bat loi
public class ValidationHandler {
    //MethodArgumentNotValidException => loi do thu vien quang ra
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidation(MethodArgumentNotValidException exception){
        String message = "";
        for (FieldError fieldError:exception.getBindingResult().getFieldErrors()){
            message += fieldError.getDefaultMessage() + "\n";
        }
        return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleValidation(Exception exception){
        return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
