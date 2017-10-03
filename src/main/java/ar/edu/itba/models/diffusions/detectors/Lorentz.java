package ar.edu.itba.models.diffusions.detectors;

/**
 * Created by Luis on 2/10/2017.
 */
public class Lorentz extends SigmaDetector{
    public Lorentz() {
    }

    public Lorentz(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public double g(double x) {
        return 1.0/(1+Math.pow(x,2)/Math.pow(sigma,2));
    }

    @Override
    public String toString() {
        return "Lorentz";
    }
}
