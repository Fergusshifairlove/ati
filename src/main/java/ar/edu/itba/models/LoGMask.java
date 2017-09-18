package ar.edu.itba.models;

/**
 * Created by root on 9/17/17.
 */
public class LoGMask extends LaplacianMask {
    public LoGMask(int size, double sigma){
        super(size);

        int halfSize=(size-1)/2;

        for(int i=-halfSize;i<=halfSize;i++){
            for(int j=-halfSize;j<=halfSize;j++){
                double normalValue= (-1/(Math.sqrt(2*Math.PI)* Math.pow(sigma, 3)))*(2-((i*i + j*j))/(sigma*sigma))*(Math.exp(-(i*i+j*j)/(2*sigma*sigma)));
                set(i,j,normalValue);
            }
        }

    }

}
