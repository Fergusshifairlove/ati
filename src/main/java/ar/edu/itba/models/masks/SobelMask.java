package ar.edu.itba.models.masks;

/**
 * Created by root on 9/16/17.
 */
public class SobelMask extends Mask{

    public SobelMask() {
        super(3);

        mask[0][0]=-1;
        mask[0][1]=-2;
        mask[0][2]=-1;

        for(int i=0;i<3;i++){
            mask[1][i]=0;
        }

        mask[2][0]=1;
        mask[2][1]=2;
        mask[2][2]=1;

    }
}
