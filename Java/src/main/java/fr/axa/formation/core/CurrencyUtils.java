package fr.axa.formation.core;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public final class CurrencyUtils {

    private CurrencyUtils() {
        throw new IllegalStateException("Do not instantiate this class");
    }

    /**
     * Convert an amount in cents (subunit) to an amount (unit)
     *
     * @param from Amount in subunit
     * @return Amount in unit
     */
    public static BigDecimal toUnit(BigInteger from) {
        if (from == null) {
            return null;
        }

        return new BigDecimal(from).divide(BigDecimal.valueOf(100), 2, RoundingMode.UNNECESSARY);
    }

    /**
     * Convert an amount (unit) to an amount in cents (subunit)
     *
     * @param from Amount in unit
     * @return Amount in subunit
     */
    public static BigInteger toCents(BigDecimal from) {
        if (from == null) {
            return null;
        }

        return from.multiply(BigDecimal.valueOf(100)).toBigInteger();
    }
}
