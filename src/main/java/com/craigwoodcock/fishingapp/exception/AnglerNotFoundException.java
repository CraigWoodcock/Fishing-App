package com.craigwoodcock.fishingapp.exception;

public class AnglerNotFoundException extends RuntimeException {
    public AnglerNotFoundException(String message) {
        super(message);
    }
}
