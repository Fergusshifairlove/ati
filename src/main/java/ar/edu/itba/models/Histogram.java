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
    private Map<Integer,Double> greyLevelMap;
    private Map<Integer, Double> cdf;
    private double minCdf;

    public Histogram(GreyImageMatrix gim){
        this.greyLevelMap = new HashMap<>();
        this.pixelCount = gim.getHeight() * gim.getWidth();

        for (int i = 0; i < 256; i++) {
            greyLevelMap.put(i, 0.0);
        }

        GreyPixel gp;
        Double count;
        for(int i=0; i<gim.getWidth(); i++){
            for (int j=0; j<gim.getHeight(); j++){
                gp = (GreyPixel) gim.getPixelColor(i, j);
                count = greyLevelMap.get(gp.getGrey());
                if (count == null)
                    greyLevelMap.put(gp.getGrey(), 1.0);
                else
                    greyLevelMap.put(gp.getGrey(),count + 1);
            }
        }

        this.greyLevelMap.entrySet().forEach(e -> greyLevelMap.put(e.getKey(),(1.0*e.getValue())/pixelCount));

        double acum = 0;
        this.cdf = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            acum += greyLevelMap.get(i);
            cdf.put(i,acum);
        }
        this.minCdf = cdf.get(0);

    }

    public double getFrequency(int category){
        return greyLevelMap.get(category);
    }

    public double getCumulativeFrequency(int category){
        return cdf.get(category);
    }
    public Iterable<Integer> getCategories() {
        return greyLevelMap.keySet();
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
}
