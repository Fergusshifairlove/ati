package ar.edu.itba.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/16/17.
 */
public class DirectionalMask {
    private List<Mask> masks;

    public DirectionalMask(Mask mask, List<Direction> directions){
        if(mask.size != 3)
            throw new IllegalArgumentException("Mask size must be 3");

        masks= new ArrayList<>();
        for(Direction direction: directions){
            masks.add(getDirectionizedMask(mask, direction));
        }
    }

    private Mask getDirectionizedMask(Mask mask, Direction direction){
        Mask directionizedMask = new Mask(3);

        switch (direction){
            case HORIZONTAL:
                directionizedMask=mask;
                break;
            case VERTICAL:
                for(int i=0;i<mask.size;i++){
                    for(int j=0;j<mask.size;j++){
                        directionizedMask.set(i,j, mask.get(j,i));
                    }
                }
                break;
            case DIAGONAL_LEFT:
                break;
            case DIAGONAL_RIGHT:
                break;
            default:
                break;
        }

        return directionizedMask;
    }

    public double[][] filterImage(double[][] image){
        return sintesis(getFilteredImages(image));
    }

    private List<double[][]> getFilteredImages(double[][] image){
        List<double[][]> filteredImages= new ArrayList<>();

        for(Mask mask: masks){
            if(mask instanceof LaplacianMask){
                System.out.println("LLEGUE");
                LaplacianMask aux = (LaplacianMask) mask;
                filteredImages.add(aux.filter(image));
            }

            filteredImages.add(mask.filterImage(image));
        }

        return filteredImages;
    }

    private double[][] sintesis(List<double[][]> filteredImages){
        if(filteredImages.size()==1){
            return filteredImages.get(0);
        }

        if(filteredImages.size()==2){
            double[][] imageX=filteredImages.get(0);
            double[][] imageY=filteredImages.get(1);
            double[][] result= new double[imageX.length][imageX[0].length]; //puede que la imagen no sea cuadrada, por eso se especifica imageX[0]

            for(int i=0;i<result.length;i++){
                for(int j=0;j<result[i].length;j++){
                    result[i][j]=Math.sqrt((imageX[i][j]*imageX[i][j])+(imageY[i][j]*imageY[i][j]));
                }
            }
            return result;
        }

        if(filteredImages.size()==4){
            //TODO
        }

        return null;
    }
}
