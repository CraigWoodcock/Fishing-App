package com.craigwoodcock.fishingapp.exception;

/**
 * Thrown when an angler is being created without an email address. Email
 * is required so anglers are uniquely identifiable — two different people
 * can share a name, but not an email — and so they can be linked to a
 * registered user account.
 */
public class MissingAnglerEmailException extends RuntimeException {
    public MissingAnglerEmailException(String message) {
        super(message);
    }
}