package ar.edu.itba.models.thresholding;

public interface ThresholdFinder {
    double findThreshold(Iterable<Double> band);
}
