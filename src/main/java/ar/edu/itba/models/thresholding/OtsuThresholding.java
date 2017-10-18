package ar.edu.itba.models.thresholding;

import ar.edu.itba.models.Histogram;

import java.util.HashSet;
import java.util.Set;


public class OtsuThresholding implements ThresholdFinder{

    @Override
    public double findThreshold(Iterable<Double> band) {
        Histogram histogram = new Histogram(band);

        double max=0.0;
        double normProb;

        double[] prob = new double[256];
        double[] mean = new double[256];

        mean[0] = 0;
        prob[0] = histogram.getFrequency(0);

        for (int t = 1; t<256 ; t++) {
            normProb = histogram.getFrequency(t);
            mean[t] = mean[t-1] + t * normProb;
            prob[t] = prob[t-1] + normProb;
            //System.out.println("prob: " + prob[t]);
            //System.out.println("mean: " + mean[t]);
        }

        double maxVar = 0.0, var;
        Set<Integer> maxVars = new HashSet();
        for (int t = 1; t<256; t++) {
            var = Math.pow(mean[255] * prob[t] - mean[t],2)/(prob[t]*(1-prob[t]));
            if(var > maxVar) {
                maxVar = var;
                maxVars.clear();
                maxVars.add(t);
            }else if (var == maxVar) {
                maxVars.add(t);
            }
        }

        return (1.0*maxVars.stream().mapToInt(Integer::intValue).sum())/maxVars.size();
    }

}
