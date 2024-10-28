package com.example.koifarm.exception;

public class InvalidConsignmentTypeException extends RuntimeException {
    public InvalidConsignmentTypeException(String message) {
        super(message);
    }
}