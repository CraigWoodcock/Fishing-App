package com.craigwoodcock.fishingapp.utils;

import com.craigwoodcock.fishingapp.exception.InvalidWeightException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Interprets and validates weights stored using the app's lb.oz notation,
 * where the two digits after the decimal point represent whole ounces
 * (0-15) rather than a decimal fraction of a pound — e.g. 5.08 means
 * 5lb 8oz, not 5.08lb.
 * <p>
 * Centralising this here means the conversion rule is defined once and
 * shared by any service that needs to validate or total weights, rather
 * than being duplicated at each call site.
 */
@Component
public class LbOzWeightConverter {

    private static final int OUNCES_PER_POUND = 16;

    /**
     * Validates that a weight is non-null, non-negative, and that its
     * decimal portion is a valid ounce value (00-15).
     *
     * @param weight the weight to validate, in lb.oz notation
     * @throws InvalidWeightException if the weight is missing or malformed
     */
    public void validate(BigDecimal weight) {
        if (weight == null) {
            throw new InvalidWeightException("Weight is required.");
        }
        if (weight.signum() < 0) {
            throw new InvalidWeightException("Weight cannot be negative.");
        }

        int ounces = extractOunces(weight);
        if (ounces > 15) {
            throw new InvalidWeightException(
                    "Weight " + weight + " is not valid lb.oz notation — "
                            + "the decimal portion must be between .00 and .15 (ounces), not " + ounces + ".");
        }
    }

    /**
     * Converts a weight in lb.oz notation into a total whole-ounce count,
     * suitable for summing multiple weights accurately.
     *
     * @param weight a validated weight in lb.oz notation
     * @return the equivalent total in ounces
     */
    public long toTotalOunces(BigDecimal weight) {
        long pounds = weight.longValue();
        int ounces = extractOunces(weight);
        return (pounds * OUNCES_PER_POUND) + ounces;
    }

    /**
     * Formats a total ounce count back into a human-readable lb.oz string,
     * e.g. 176 ounces becomes "11lb 0oz".
     *
     * @param totalOunces the total weight in ounces
     * @return a formatted "Xlb Yoz" string
     */
    public String formatTotalOunces(long totalOunces) {
        long pounds = totalOunces / OUNCES_PER_POUND;
        long remainderOunces = totalOunces % OUNCES_PER_POUND;
        return pounds + "lb " + remainderOunces + "oz";
    }

    /**
     * Extracts the ounces component (the two digits after the decimal
     * point) from a lb.oz notation weight.
     */
    private int extractOunces(BigDecimal weight) {
        BigDecimal fractional = weight.subtract(BigDecimal.valueOf(weight.longValue()));
        return fractional.movePointRight(2).setScale(0, RoundingMode.HALF_UP).intValueExact();
    }

    /**
     * Formats a single catch's lb.oz weight for display, e.g. 5.08 becomes
     * "5lb 8oz".
     *
     * @param weight a validated weight in lb.oz notation
     * @return a formatted "Xlb Yoz" string
     */
    public String format(BigDecimal weight) {
        long pounds = weight.longValue();
        int ounces = extractOunces(weight);
        return pounds + "lb " + ounces + "oz";
    }
}