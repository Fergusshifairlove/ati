package ar.edu.itba.models;

import java.util.Arrays;

/**
 * Created by Nicolas Castano on 8/29/17.
 */
public class MedianMask extends Mask{

    public MedianMask(int size) {
        super(size);
    }

    public double applyFilter(double[] values){
        Arrays.sort(values);
        return (double)values[(values.length - 1) / 2];
    }
}
