package ar.edu.itba.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicolas Castano on 8/25/17.
 */
public class Histogram {

    private int pixelCount=0;
    private Map<Integer,Double> greyLevelMap;


    public Histogram(GreyImageMatrix gim){
        this.greyLevelMap = new HashMap<>();
        for(int i=0; i <= 255; i++){
            greyLevelMap.put(i, 0.0);
        }

        GreyPixel gp;
        for(int i=0; i<gim.getWidth(); i++){
            for (int j=0; j<gim.getHeight(); j++){
                gp = (GreyPixel) gim.getPixelColor(i, j);
                greyLevelMap.put(gp.getGrey(), greyLevelMap.get(gp.getGrey())+1);
                pixelCount++;
            }
        }
        greyLevelMap.entrySet().stream().map(e -> greyLevelMap.put(e.getKey(),e.getValue()/pixelCount));

    }

    private double getSumOfFrequencies(){
        double s=0;

        for(Double i: greyLevelMap.values()){
            s+= (i/pixelCount);
        }

        return s;

    }

    private double getMinFrequency(){
        double sMin=greyLevelMap.get(0);

        for(Double i: greyLevelMap.values()){
            if(i<sMin){
                sMin=i;
            }
        }

        return sMin/pixelCount;
    }

    public int discreteTransformation(){
        double min= getMinFrequency();
        return (int)(((getSumOfFrequencies()-min)/(1-min)) + 0.5);
    }

    public double getFrequency(int category){
        return greyLevelMap.get(category);
    }

    public Iterable<Integer> getCategories() {
        return greyLevelMap.keySet();
    }
}
