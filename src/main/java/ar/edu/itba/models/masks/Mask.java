package ar.edu.itba.models.masks;

import java.util.Arrays;

/**
 * Created by Nicolas Castano on 8/28/17.
 */
public class Mask {

    double mask[][];
    protected int size=0;
//    protected List<Integer> list;

    public Mask(int size){
        mask= new double[size][size];
        this.size = size;
//        list = new ArrayList<>();
    }

    public double get(int x, int y)throws IllegalArgumentException{
        if(x >=0 && x < size && y>=0 && y<size){
            return mask[x][y];
        }else{
            throw new IllegalArgumentException("x or y out of bounds");
        }
    }

    public void set(int x, int y, double value)throws IllegalArgumentException{
        if(x >=0 && x < size && y>=0 && y<size){
            mask[x][y] = value;
        }else{
            throw new IllegalArgumentException("x or y out of bounds");
        }

    }

    public String toString(){
        StringBuffer buffer=new StringBuffer();
        buffer.append("Mask values:\n");
        for(double[] currentLine:mask){
            buffer.append(Arrays.toString(currentLine));
            buffer.append("\n");
        }
        return buffer.toString();

    }

    public double applyFilter(double[] values) {
        double accum = 0.0;
        for(int i =0; i<size; i++){
            for (int j = 0; j < size; j++) {
                accum += get(i, j) * values[i*size + j];
            }
        }
        return accum;
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
                        }
                    }
                    newImage[i][j] = applyFilter(values);

                }
            }
        }
        return newImage;
    }

}

