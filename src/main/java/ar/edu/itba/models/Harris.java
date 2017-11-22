package ar.edu.itba.models;

import ar.edu.itba.constants.Direction;
import ar.edu.itba.models.masks.DirectionalMask;
import ar.edu.itba.models.masks.Filter;
import ar.edu.itba.models.masks.GaussianMask;
import ar.edu.itba.models.masks.SobelMask;

/**
 * Created by Luis on 21/11/2017.
 */
public class Harris implements Filter {

    private double threshold;

    public Harris(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public double[][] filterImage(double[][] image) {
        SobelMask detector = new SobelMask();
        double [][] dx, dy;
        double [][] dx2, dy2, dxy;
        int width = image.length, height = image[0].length;
        GaussianMask mask = new GaussianMask(7,2);
        dx = DirectionalMask.getDirectionizedMask(detector, Direction.HORIZONTAL).filterImage(image);
        dy = DirectionalMask.getDirectionizedMask(detector, Direction.VERTICAL).filterImage(image);
        dxy = new double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                dxy[i][j] = dx[i][j] * dy[i][j];
                dx[i][j] = Math.pow(dx[i][j],2);
                dy[i][j] = Math.pow(dy[i][j],2);
            }
        }

        dx2 = mask.filterImage(dx);
        dy2 = mask.filterImage(dy);
        dxy = mask.filterImage(dxy);

        double k = 0.04;

        double max = Double.MIN_VALUE;
        double min = Double.MAX_VALUE;
        double[][] cim = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cim[i][j] = (dx2[i][j] * dy2[i][j] - Math.pow(dxy[i][j], 2)) - k * Math.pow(dx2[i][j] + dy2[i][j], 2);
                max = Math.max(max, cim[i][j]);
                min = Math.min(min, cim[i][j]);
            }
        }

        double scale = 1 -  threshold / 100;
        double limitValue = min + (max-min)*scale;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (cim[i][j] < limitValue) {
                    cim[i][j]= 0.0;
                }
            }
        }
        return cim;
    }
}
