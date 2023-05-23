package com.shelter.exceptions;

public class WalkNotFoundException extends RuntimeException{
    public WalkNotFoundException(String message) {
        super(message);
    }
}
