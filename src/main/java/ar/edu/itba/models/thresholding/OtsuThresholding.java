package ar.edu.itba.models.thresholding;

import ar.edu.itba.models.Histogram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OtsuThresholding implements ThresholdFinder{

    @Override
    public double findThreshold(Iterable<Double> band) {
        Histogram histogram = new Histogram(band);

        List<Double> acumSums = new ArrayList<>();
        List<Double> acumMeans = new ArrayList<>();
        Double globalMean, mean=0.0, var, threshold=0.0;
        int t = 0;

        for (Integer b: histogram.getCategories()) {
            acumSums.add(histogram.getCumulativeFrequency(b)/histogram.getPixelCount());
            mean = 0.0;
            for (int i=0; i<=t; i++) {
                mean += histogram.getFrequency(b) * i;
            }
            acumMeans.add(mean);
            t++;
        }
        globalMean = mean;
        double maxVar;
        maxVar = Math.pow(globalMean*acumSums.get(0) - acumMeans.get(0),2);
        maxVar = maxVar/(acumSums.get(0)*(1 - acumSums.get(0)));
        Set<Integer> maxThresholds = new HashSet<>();
        maxThresholds.add(0);
        for (int i = 0; i <= t-1; i++) {
            var = Math.pow(globalMean*acumSums.get(i) - acumMeans.get(i),2);
            var = var/(acumSums.get(i)*(1 - acumSums.get(i)));
            if (var > maxVar) {
                maxThresholds.clear();
                maxThresholds.add(i);
                maxVar = var;
            }else if (var == maxVar) {
                maxThresholds.add(i);
            }
        }

        for (Integer i: maxThresholds) {
            System.out.println(i);
            threshold += i;
        }

        return (1.0*threshold)/maxThresholds.size();
    }


}
