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
        double[][] aux;
        double d, c, accum;

        for (int i = 0; i < width; i++) {
            newBand[i][0] = band[i][0];
        }
        for (int j = 0; j < height; j++) {
            newBand[0][j] = band[0][j];
        }
        for (int i = 0; i < width; i++) {
            newBand[i][height-1] = band[i][height-1];
        }
        for (int j = 0; j < height; j++) {
            newBand[width-1][j] = band[width-1][j];
        }


        for (int t = 1; t < times; t ++) {
            for (int i = 1; i < width-1; i++) {
                for (int j = 1; j < height-1; j++) {
                    accum = 0;
                    for (int k = 0; k < 4; k++) {
                        d = band[i + disp[k][0]][j + disp[k][1]] - band[i][j];
                        c = detector.applyAsDouble(d * band[i][j]);
                        accum += c * d;
                    }
                    newBand[i][j] = band[i][j] + this.lambda * accum;
                }
            }
            //switch for next iter
            aux = band;
            band = newBand;
            //garbage
            newBand = aux;
        }

        return band;
    }
}
