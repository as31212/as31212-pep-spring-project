package com.example.exception;

public class InvalidMessageTextException extends Exception {
    public InvalidMessageTextException(String message){
        super(message);
    }
}
