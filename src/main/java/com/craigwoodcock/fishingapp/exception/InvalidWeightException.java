package com.craigwoodcock.fishingapp.exception;

/**
 * Thrown when a catch's weight is missing, negative, or does not represent
 * a valid lb.oz value (i.e. the decimal portion is not between .00 and .15).
 */
public class InvalidWeightException extends RuntimeException {
    public InvalidWeightException(String message) {
        super(message);
    }
}
