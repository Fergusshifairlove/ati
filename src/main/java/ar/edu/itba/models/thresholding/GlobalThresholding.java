package ar.edu.itba.models.thresholding;

import java.util.ArrayList;
import java.util.List;

public class GlobalThresholding implements ThresholdFinder{

    private double initialThreshold;
    private double delta;

    public GlobalThresholding(double initialThreshold, double delta) {
        this.initialThreshold = initialThreshold;
        this.delta = delta;
    }

    @Override
    public double findThreshold(Iterable<Double> band) {

        Double threshold = initialThreshold, prevThreshold, m1, m2;
        List<Double> g1 = new ArrayList<>();
        List<Double> g2 = new ArrayList<>();

        do {
            for (Double d : band) {
                if (d > threshold)
                    g1.add(d);
                else
                    g2.add(d);
            }

            m1 = g1.stream().mapToDouble(d -> d).sum() / g1.size();
            m2 = g2.stream().mapToDouble(d -> d).sum() / g2.size();

            g1.clear();
            g2.clear();

            prevThreshold = threshold;
            threshold = (m1 + m2) / 2;
        } while (Math.abs(threshold - prevThreshold) < this.delta);

        return threshold;
    }
}
