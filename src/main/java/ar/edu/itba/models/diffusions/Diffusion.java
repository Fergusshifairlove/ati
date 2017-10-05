package ar.edu.itba.models.diffusions;

import java.util.Arrays;
import java.util.function.ToDoubleFunction;

/**
 * Created by Luis on 2/10/2017.
 */
public abstract class Diffusion {
    private double lambda;
    private ToDoubleFunction<Double> detector;
    private static Integer[][] disp = {{1,0},{-1,0},{0,1},{0,-1}};

    public Diffusion(double lambda, ToDoubleFunction<Double> detector) {
        this.lambda = lambda;
        this.detector = detector;
    }

    public double[][] difuse(double[][] band, int times) {
        int width = band.length;
        int height = band[0].length;
        double[][] newBand = new double[width][height];
        double[][] oldBand = new double[width][height];

        for (int i = 0; i < width; i++) {
            System.arraycopy(band[i], 0, oldBand[i], 0, height);
        }

        double[][] aux;
        double d, c, accum;

        for (int i = 0; i < width; i++) {
            newBand[i][0] = oldBand[i][0];
        }
        for (int j = 0; j < height; j++) {
            newBand[0][j] = oldBand[0][j];
        }
        for (int i = 0; i < width; i++) {
            newBand[i][height-1] = oldBand[i][height-1];
        }
        for (int j = 0; j < height; j++) {
            newBand[width-1][j] = oldBand[width-1][j];
        }


        for (int t = 1; t < times + 1; t ++) {
            for (int i = 1; i < width-1; i++) {
                for (int j = 1; j < height-1; j++) {
                    accum = 0;
                    for (int k = 0; k < 4; k++) {
                        d = oldBand[i + disp[k][0]][j + disp[k][1]] - oldBand[i][j];
                        c = detector.applyAsDouble(d);
                        accum += c * d;
                    }
                    newBand[i][j] = oldBand[i][j] + this.lambda * accum;
                }
            }
            //switch for next iter
            aux = oldBand;
            oldBand = newBand;
            //garbage
            newBand = aux;
        }

        return oldBand;
    }
}
