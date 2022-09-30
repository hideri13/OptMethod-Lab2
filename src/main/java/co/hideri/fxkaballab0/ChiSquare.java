package co.hideri.kaballab0fx;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathArrays;
import org.apache.commons.math3.util.Pair;

public class ChiSquare {
    //Apache embedded method
    private double chiSquare(final double[] expected, final long[] observed)
            throws NotPositiveException, NotStrictlyPositiveException,
            DimensionMismatchException {

        if (expected.length < 2) {
            throw new DimensionMismatchException(expected.length, 2);
        }
        if (expected.length != observed.length) {
            throw new DimensionMismatchException(expected.length, observed.length);
        }
        MathArrays.checkPositive(expected);
        MathArrays.checkNonNegative(observed);

        double sumExpected = 0d;
        double sumObserved = 0d;
        for (int i = 0; i < observed.length; i++) {
            sumExpected += expected[i];
            sumObserved += observed[i];
        }
        double ratio = 1.0d;
        boolean rescale = false;
        if (FastMath.abs(sumExpected - sumObserved) > 10E-6) {
            ratio = sumObserved / sumExpected;
            rescale = true;
        }
        double sumSq = 0.0d;
        for (int i = 0; i < observed.length; i++) {
            if (rescale) {
                final double dev = observed[i] - ratio * expected[i];
                sumSq += dev * dev / (ratio * expected[i]);
            } else {
                final double dev = observed[i] - expected[i];
                sumSq += dev * dev / expected[i];
            }
        }
        return sumSq;

    }

    public Pair<Double, Double> chiSquareTest(final double[] expected, final long[] observed, final int ddof)
            throws NotPositiveException, NotStrictlyPositiveException,
            DimensionMismatchException, MaxCountExceededException {

        // pass a null rng to avoid unneeded overhead as we will not sample from this distribution
        final ChiSquaredDistribution distribution =
                new ChiSquaredDistribution(null, expected.length - 1.0 - ddof);
        double K = chiSquare(expected, observed);
        double p = 1.0 - distribution.cumulativeProbability(K);
        return new Pair<>(K , p);
    }
}

