package com.craigwoodcock.fishingapp.exception;

/**
 * Thrown when a catch's recorded time falls before the start of the
 * session it's being logged against.
 */
public class InvalidCatchTimeException extends RuntimeException {
    public InvalidCatchTimeException(String message) {
        super(message);
    }
}
