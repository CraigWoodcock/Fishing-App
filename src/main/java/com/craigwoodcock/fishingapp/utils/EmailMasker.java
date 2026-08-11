package com.craigwoodcock.fishingapp.utils;

/**
 * Masks an email address for display in the admin panel, keeping
 * the first two characters of the local part and the full domain
 * visible, e.g. "Bill.smick@gmail.com" -> "bi***@gmail.com".
 */
public final class EmailMasker {

    private EmailMasker() {
    }

    public static String mask(String email) {
        int atIndex = email.indexOf('@');
        if (atIndex <= 1) {
            return "***" + email.substring(atIndex);
        }
        String localPart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex);
        String visiblePrefix = localPart.substring(0, Math.min(3, localPart.length()));
        return visiblePrefix + "***" + domainPart;
    }
}