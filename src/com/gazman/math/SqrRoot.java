package com.gazman.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Source: http://stackoverflow.com/questions/4407839/how-can-i-find-the-square-root-of-a-java-biginteger
 */

public class SqrRoot {
    private static final int PRECISION = 20;
    private static BigInteger multiplier = BigInteger.valueOf(10).pow(PRECISION * 2);
    private static BigDecimal root = BigDecimal.valueOf(10).pow(PRECISION);
    private static BigInteger two = BigInteger.valueOf(2L);

    public static BigDecimal bigDecimalSqRootFloor(BigInteger value)
            throws IllegalArgumentException {
        BigInteger result = bigIntSqRootFloor(value.multiply(multiplier));
        //noinspection BigDecimalMethodWithoutRoundingCalled
        return new BigDecimal(result).divide(root);
    }

    public static BigInteger bigIntSqRootFloor(BigInteger value)
            throws IllegalArgumentException {
        if (checkTrivial(value)) {
            return value;
        }
        if (value.bitLength() < 64) { // Can be cast to long
            double sqrt = Math.sqrt(value.longValue());
            return BigInteger.valueOf((long) sqrt);
        }
        // starting with y = value / 2 avoids magnitude issues with value squared
        BigInteger y = two.pow(value.bitLength() / 2);
        BigInteger result = value.divide(y);
        while (y.compareTo(result) > 0) {
            y = result.add(y).divide(two);
            result = value.divide(y);
        }
        return y;
    }

    public static BigInteger bigIntSqRootCeil(BigInteger x)
            throws IllegalArgumentException {
        BigInteger y = bigIntSqRootFloor(x);
        if (x.compareTo(y.multiply(y)) == 0) {
            return y;
        }
        return y.add(BigInteger.ONE);
    }

    private static boolean checkTrivial(BigInteger x) {
        if (x == null) {
            throw new NullPointerException("x can't be null");
        }
        if (x.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Negative argument.");
        }

        return x.equals(BigInteger.ZERO) || x.equals(BigInteger.ONE);
    }
}
