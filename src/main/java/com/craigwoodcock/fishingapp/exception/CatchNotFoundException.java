package com.craigwoodcock.fishingapp.exception;

public class CatchNotFoundException extends RuntimeException {
    public CatchNotFoundException(String message) {
        super(message);
    }
}
