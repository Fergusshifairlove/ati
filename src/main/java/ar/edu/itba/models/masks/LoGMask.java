package ar.edu.itba.models.masks;

import ar.edu.itba.models.masks.LaplacianMask;

/**
 * Created by root on 9/17/17.
 */
public class LoGMask extends Mask{
    private static final int WHITE = 255;
    private  double delta;

    public LoGMask(int size, double sigma,double delta){
        super(size);
        this.delta=delta;

        int halfSize=(size-1)/2;

        for(int i=-halfSize;i<=halfSize;i++){
            for(int j=-halfSize;j<=halfSize;j++){
                double member1= -1/(Math.sqrt(2*Math.PI)* Math.pow(sigma, 3));
                double member2=2- (  (i*i+ j*j)/(sigma*sigma) );
                double member3=Math.exp(-(i*i+j*j)/(2*sigma*sigma));

                double normalValue =member1*member2*member3;

                set(i+halfSize, j+halfSize, normalValue);
            }
        }

        System.out.println(toString());


    }

    public double[][] filter(double[][] image) {
        double[][] maskApplied;

        maskApplied = filterImage(image);

        return crossByZero(maskApplied);
    }

    private double[][] crossByZero(double[][] maskApplied) {
        int height = maskApplied.length;
        int width = maskApplied[0].length;
        double[][] ans = new double[height][width];
        double[][] crossByRow = new double[height][width];
        double[][] crossByCol = new double[height][width];

        //init with cero
        for (int i = 0; i <height ; i++) {
            for (int j = 0; j <width ; j++) {
                crossByRow[i][j]=0;
                crossByCol[i][j]=0;

            }

        }

        //cross by zero per row
        for (int i = 0; i < height; i++) {
            double previous = maskApplied[i][0];
            for (int j = 1; j < width; j++) {
                double current = maskApplied[i][j];

                if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {
                    if ((Math.abs(current + previous)) > delta) {
                        crossByRow[i][j] = WHITE;
                    }else{
                        crossByRow[i][j]=0.0;
                    }
                }
                previous = current;
            }
        }

        //cross by zero per col
        for (int j = 0; j < width; j++) {
            double previous = maskApplied[0][j];
            for (int i = 1; i < height; i++) {
                double current = maskApplied[i][j];

                if ((previous > 0 && current < 0) || (previous < 0 && current > 0)) {
                    if ((Math.abs(current + previous)) > delta) {
                        crossByCol[i][j] = WHITE;
                    } else{
                        crossByCol[i][j]=0.0;
                    }
                }
                previous = current;
            }
        }

        //intersection
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (crossByRow[i][j] == WHITE || crossByCol[i][j] == WHITE) {
                    ans[i][j] = WHITE;
                } else {
                    ans[i][j] = 0;
                }
            }
        }

        return ans;
    }

}
