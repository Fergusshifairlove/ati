package ar.edu.itba.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Nicolas Castano on 8/25/17.
 */
public class Histogram {

    private int pixelCount;
    private Map<Integer,Double> df;
    private Map<Integer, Double> cdf;
    private double minCdf;

    public Histogram(Iterable<Double> pixels){
        this.df = new HashMap<>();
        this.pixelCount = 0;

        for (int i = 0; i < 256; i++) {
            df.put(i, 0.0);
        }

        Double count;
        for (Double pixel: pixels) {
            pixelCount++;
            Integer p = pixel.intValue();
            count = df.get(p);
            if (count == null)
                df.put(p, 1.0);
            else
                df.put(p,count + 1);
        }


        this.df.entrySet().forEach(e -> df.put(e.getKey(),(1.0*e.getValue())/pixelCount));

        double acum = 0;
        this.cdf = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            acum += df.get(i);
            cdf.put(i,acum);
        }
        this.minCdf = cdf.get(0);

    }

    public double getFrequency(int category){
        return df.get(category);
    }
    public double getCumulativeFrequency(int category){
        return cdf.get(category);
    }

    public Iterable<Integer> getCategories() {
        return df.keySet();
    }
    public int getCategoryAmmount() {
        return df.keySet().size();
    }

    public double getCDF(Integer pixel) {
        return this.cdf.get(pixel);
    }

    public double getMinCdf() {
        return this.minCdf;
    }

    public double equalize(double pixel) {
        System.out.println(pixel);
        double res = Math.round((255.0 * (cdf.get((int) pixel) - this.minCdf) / (1 - minCdf)));
        System.out.println(res);
        return res;
    }

    public int getPixelCount() {
        return pixelCount;
    }

}
