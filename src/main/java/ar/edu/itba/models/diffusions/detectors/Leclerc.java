package ar.edu.itba.models.diffusions.detectors;

/**
 * Created by Luis on 2/10/2017.
 */
public class Leclerc extends SigmaDetector{

    public Leclerc() {
    }

    public Leclerc(double sigma) {
        this.sigma = sigma;
    }

    @Override
    public double g(double x) {
        return Math.exp(-Math.pow(x,2)/Math.pow(sigma,2));
    }

    @Override
    public String toString() {
        return "Leclerc";
    }
}
