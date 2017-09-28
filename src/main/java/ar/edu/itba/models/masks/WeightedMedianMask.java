package ar.edu.itba.models.masks;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Nicolas Castano on 8/29/17.
 */
public class WeightedMedianMask extends Mask {

    private Map<Double,Double> repeats;
    public WeightedMedianMask(int size) {
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
                            Double prev = repeats.get(image[i+k-halfSize][j+k2-halfSize]);
                            if(prev == null){
                                repeats.put(image[i+k-halfSize][j+k2-halfSize], mask[k][k2]);
                            }else{
                                repeats.put(image[i+k-halfSize][j+k2-halfSize], mask[k][k2]+prev);
                            }

                        }
                    }
                    newImage[i][j] = applyFilter(repeats);
                    repeats.clear();

                }
            }
        }
        return newImage;
    }

    public double applyFilter(Map<Double,Double> map){

        List<Double> values = map.keySet().stream().collect(Collectors.toList());
        Collections.sort(values);

        double[] repetitions = new double[16];
        int k=0;
        for (Double value: values) {
            for(int j=0;j< repeats.get(value);j++){
                repetitions[k++]=value;
            }
        }

        return repetitions[(repetitions.length - 1) / 2];
    }

}
