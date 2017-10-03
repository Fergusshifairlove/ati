package ar.edu.itba.models.diffusions.detectors;

/**
 * Created by Luis on 3/10/2017.
 */
public abstract class SigmaDetector implements Detector{
    protected double sigma;

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public abstract double g(double x);
}
