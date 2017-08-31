package ar.edu.itba.models;

import java.util.HashMap;
import java.util.Map;

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

        System.out.println(this.toString());

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
                            System.out.println("VALUES");
                            System.out.println(values[k*size+k2]);
                            Double prev = repeats.get(image[i+k-halfSize][j+k2-halfSize]);
                            if(prev == null){
                                repeats.put(image[i+k-halfSize][j+k2-halfSize], mask[k][k2]);
                            }else{
                                repeats.put(image[i+k-halfSize][j+k2-halfSize], mask[k][k2]+prev);
                            }

                        }
                    }
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println(repeats.toString());
                    System.out.println("PASE ESTO");
                    newImage[i][j] = applyFilter(repeats);
                    repeats.clear();

                }
            }
        }
        return newImage;
    }

    public double applyFilter(Map<Double,Double> map){

        double[] repetitions = new double[16];
//        map.values().stream().sorted(Double::compare).collect();
        double aux;
        int k=0;

//        System.out.println("longitud de values: "+values.length);
//        for(int i=0; i< values.length; i++){
//            aux = values[i];
//            System.out.println("aux: " + aux);
//            System.out.println("lo que devuelve el mapa: "+ repeats.get(aux));
//            for(int j=0;j< repeats.get(aux);j++){
//                repetitions[k++]=aux;
//            }
//        }

        return (double)repetitions[(repetitions.length - 1) / 2];
    }

}