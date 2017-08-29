package ar.edu.itba.models;

/**
 * Created by Nicolas Castano on 8/29/17.
 */
public class GaussianMask extends Mask{
    public GaussianMask(int size, double sigma) {
        super(size);

        int halfSize = (size-1)/2;
        double normalValue;
        double acum=0.0;
        //Fill matrix with numbers between 0 and 1
        for(int i=-halfSize;i<=halfSize;i++){
            for(int j=-halfSize;j<=halfSize;j++){
                normalValue = (1/(2*Math.PI*sigma*sigma))*Math.exp((-i*i -j*j)/(sigma*sigma));
                mask[i+halfSize][j+halfSize] = normalValue;
            }
        }

        //Sum mask values
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                acum += mask[i][j];
            }
        }

        //Divide all mask values by accum
        for(int i=0; i<size;i++){
            for(int j=0; j<size; j++){
                    mask[i][j] /= acum;
            }
        }

    }
}
