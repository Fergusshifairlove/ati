package ar.edu.itba.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 8/29/17.
 */
public class PounderedMedianMask extends Mask {

    private Map<Double,Double> repeats;
    public PounderedMedianMask(int size) {
        super(size);
        set(0,0,1);
        set(0,2,1);
        set(2,0,1);
        set(2,2,1);
        set(1,0,2);
        set(0,1,2);
        set(1,2,2);
        set(2,1,2);
        set(1,1,4);

        repeats = new HashMap<>();
    }

    public double[][] filterImage(double[][] image){
        double[][] newImage = new double[image.length][image[0].length];
        int halfSize = (size-1)/2;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                if(i<halfSize || j<halfSize || i>(image.length-1-halfSize) || j>(image[i].length-1-halfSize)){
                    newImage[i][j] = image[i][j];
                } else {
                    double[] values = new double[size*size];
                    for (int k = 0; k < size; k++) {
                        for (int k2 = 0; k2 < size; k2++) {
                            values[k*size+k2] = image[i+k-halfSize][j+k2-halfSize];
                            repeats.put(image[i+k-halfSize][j+k2-halfSize], mask[i+k-halfSize][j+k2-halfSize]);
                        }
                    }
                    newImage[i][j] = applyFilter(values);

                }
            }
        }
        return newImage;
    }

    public double applyFilter(double[] values){
        Arrays.sort(values);
        double[] repetitions = new double[16];
        double aux;
        int k=0;

        for(int i=0; i< repetitions.length; i++){
            aux = values[i];
            for(int j=0;j< repeats.get(aux);j++){
                repetitions[k++]=aux;
            }
        }

        return (double)repetitions[(repetitions.length - 1) / 2];
    }

}
