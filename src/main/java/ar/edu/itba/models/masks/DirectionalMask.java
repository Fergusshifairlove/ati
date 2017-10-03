package ar.edu.itba.models.masks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/16/17.
 */
public class DirectionalMask {
    private List<Mask> masks;
    private List<Double> params;

    public DirectionalMask(Mask mask, List<Direction> directions,List<Double> params){
        if(mask.size != 3)
            throw new IllegalArgumentException("Mask size must be 3");

        masks= new ArrayList<>();

        if(params !=null){
            this.params = new ArrayList<>();

            for(Double i: params){
                this.params.add(i);
            }
        }


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
                directionizedMask.set(0, 0, mask.get(0, 1));
                directionizedMask.set(0, 1, mask.get(0, 2));
                directionizedMask.set(0, 2, mask.get(1, 2));
                directionizedMask.set(1, 0, mask.get(0, 0));
                directionizedMask.set(1, 1, mask.get(1, 1));			//	b	c	f
                directionizedMask.set(1, 2, mask.get(2, 2));			//	a	e	i
                directionizedMask.set(2, 0, mask.get(1, 0));			//	d	g	h
                directionizedMask.set(2, 1, mask.get(2, 0));
                directionizedMask.set(2, 2, mask.get(2, 1));
                break;
            case DIAGONAL_RIGHT:
                directionizedMask.set(0, 0, mask.get(1, 0));
                directionizedMask.set(0, 1, mask.get(0, 0));			//	d	a	b
                directionizedMask.set(0, 2, mask.get(0, 1));			//	g	e	c
                directionizedMask.set(1, 0, mask.get(2, 0));			//	h	i	f
                directionizedMask.set(1, 1, mask.get(1, 1));
                directionizedMask.set(1, 2, mask.get(0, 2));
                directionizedMask.set(2, 0, mask.get(2, 1));
                directionizedMask.set(2, 1, mask.get(2, 2));
                directionizedMask.set(2, 2, mask.get(1, 2));
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


        System.out.println(masks.toString());
        for(Mask mask: masks){

            if(mask instanceof LaplacianMask){
                LaplacianMask aux = new LaplacianMask(3);
                filteredImages.add(aux.filter(image));
            }else if(mask instanceof ZeroLaplacianMask){
                ZeroLaplacianMask aux = new ZeroLaplacianMask(3,this.params.get(0));
                filteredImages.add(aux.filter(image));
            }else if(mask instanceof LoGMask){
                LoGMask aux = new LoGMask((int) (4*this.params.get(0)+1),this.params.get(0),this.params.get(1));
                filteredImages.add(aux.filter(image));
            }else{
                filteredImages.add(mask.filterImage(image));
            }


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
            double[][] imageH=filteredImages.get(0);
            double[][] imageV=filteredImages.get(1);
            double[][] imageDL=filteredImages.get(2);
            double[][] imageDR=filteredImages.get(3);
            double[][] result=new double[imageH.length][imageH[0].length];

            for(int i=0;i<result.length;i++){
                for(int j=0;j<result[i].length;j++){
                    result[i][j]=Math.max(Math.max(imageH[i][j],imageV[i][j]),Math.max(imageDL[i][j],imageDR[i][j]));
                }
            }
            return result;

        }

        return null;
    }
}
