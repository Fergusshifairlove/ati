package ar.edu.itba.models;

/**
 * Created by Nicolas Castano on 8/29/17.
 */
public class MeanMask extends Mask{

    public MeanMask(int size) {
        super(size);

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                mask[i][j]=1.0/(size*size);
            }
        }
    }
}
