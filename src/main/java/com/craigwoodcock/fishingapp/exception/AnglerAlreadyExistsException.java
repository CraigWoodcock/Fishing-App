package com.craigwoodcock.fishingapp.exception;

public class AnglerAlreadyExistsException extends RuntimeException {
    public AnglerAlreadyExistsException(String message) {
        super(message);
    }
}
