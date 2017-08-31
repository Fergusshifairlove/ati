package ar.edu.itba.models;

/**
 * Created by Nicolas Castano on 8/29/17.
 */
public class HighPassMask extends Mask{

    public HighPassMask(int size) {
        super(size);

        double nucleo = (size - 1)/2;

        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if(i == nucleo && j== nucleo){
                    mask[i][j]=((double)size*size - 1)/(size*size);
                }else{
                    mask[i][j]= -1.0/(size*size);
                }
            }
        }
        System.out.println(this);
    }

    public double[][] filterImage(double[][] image){
        double[][] filteredImage = super.filterImage(image);
        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        int halfSize = (size-1)/2;
        for (int i = halfSize; i < filteredImage.length - halfSize; i++) {
            for (int j = halfSize; j < filteredImage[i].length - halfSize; j++) {
                if (i == halfSize && j == halfSize) {
                    max = filteredImage[i][j];
                    min = max;
                }
                if (filteredImage[i][j] > max)
                    max = filteredImage[i][j];
                if (filteredImage[i][j] < min)
                    min = filteredImage[i][j];
            }
        }
        if((max <= 255) && (min >= 0)){
            System.out.println("OK");
            return filteredImage;
        }

        System.out.println(max);
        System.out.println(min);
        System.out.println("NOT OK");
        //En caso de que haya algun pixel que realce mucho la imagen, ajusto
        double aux = 255/(max-min);
        for (int i = halfSize; i < filteredImage.length- halfSize; i++) {
            for (int j = halfSize; j < filteredImage[i].length - halfSize; j++) {
                filteredImage[i][j] = aux * (filteredImage[i][j] - min);
            }
        }
        return filteredImage;

    }


}
