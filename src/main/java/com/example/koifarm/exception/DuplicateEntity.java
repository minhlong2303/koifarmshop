package com.example.koifarm.exception;

public class DuplicateEntity extends RuntimeException{
    public DuplicateEntity(String message){
        super(message);
    }
}
