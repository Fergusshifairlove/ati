package ar.edu.itba.models;

import ar.edu.itba.constants.Direction;
import ar.edu.itba.models.masks.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

public class Canny implements Filter{

    private final static double ANGLE_0_45 = 22.5;
    private final static double ANGLE_45_90 = 67.5;
    private final static double ANGLE_90_135 = 112.5;
    private final static double ANGLE_135_0 = 157.5;

    private double[] sigmas;
    private BinaryOperator<Double> synthesizer;
    private BinaryOperator<Double> combiner;
    private double t1, t2;

    public Canny(double[] sigmas, BinaryOperator<Double> synthesizer, BinaryOperator<Double> combiner,  double t1, double t2) {
        this.sigmas = sigmas;
        this.synthesizer = synthesizer;
        this.combiner = combiner;
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public double[][] filterImage(double[][] image) {
        int cant = this.sigmas.length;
        GaussianMask mask;
        SobelMask detector = new SobelMask();
        double [][] current, dx, dy;
        double[] thresholds;
        Direction[][] directions = new Direction[image.length][image[0].length];
        List<double[][]> images = new ArrayList();
        for (int i = 0; i < cant; i++) {
            mask = new GaussianMask((int)(2*Math.ceil(2*sigmas[i])+1), sigmas[i]);
            current = mask.filterImage(image);
            dx = DirectionalMask.getDirectionizedMask(detector, Direction.HORIZONTAL).filterImage(current);
            dy = DirectionalMask.getDirectionizedMask(detector, Direction.VERTICAL).filterImage(current);
            for (int x = 0; x < current.length; x++) {
                for (int y = 0; y < current[x].length; y++) {
                    current[x][y] = synthesizer.apply(dx[x][y], dy[x][y]);
                    directions[x][y] = getAngle(Math.atan2(dx[x][y], dy[x][y]) + 180);
                }
            }


            //thresholds = this.findThresholds(image);
            images.add(this.hysteresisThresholding(this.suppression(current, directions), t1, t2));
        }

        return images.stream().reduce((m1, m2) -> combine(m1, m2, combiner)).get();
    }

    private static double [][] combine(double[][] m1, double [][] m2, BinaryOperator<Double> combiner) {
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[i].length; j++) {
                m1[i][j] = combiner.apply(m1[i][j], m2[i][j]);
            }
        }
        return m1;
    }

    private static Direction getAngle(double angle) {
        angle = angle%180;
        if(angle<ANGLE_0_45)
            return Direction.HORIZONTAL;
        if(angle<ANGLE_45_90)
            return Direction.DIAGONAL_RIGHT;
        if(angle<ANGLE_90_135)
            return Direction.VERTICAL;
        if(angle<ANGLE_135_0)
            return Direction.DIAGONAL_LEFT;
        return Direction.HORIZONTAL;
    }

    private double[][] suppression(double[][] image, Direction[][] borderDirection) {
        double current, next;
        int xdisp, ydisp;
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                current = image[i][j];
                if (current <= 0)
                    continue;
                xdisp = borderDirection[i][j].getxStep();
                ydisp = borderDirection[i][j].getyStep();
                if (i + xdisp >= image.length || j + ydisp >= image[i].length || i + xdisp < 0 || j + ydisp< 0)
                    continue;
                next = image[i + xdisp][j + ydisp];
                if (next >= current) {
                    image[i][j] = 0.0;
                }
            }
        }
        return image;
    }

    private double[][] hysteresisThresholding(double[][] image, double t1, double t2) {
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                if(image[i][j] < t2)
                    image[i][j] = 255.0;
                else if (image[i][j] < t1)
                    image[i][j] = 0.0;
                else {
                    if (i != 0 && j != 0) {
                        if (image[i-1][j] == 255.0 || image[i][j-1] == 255)
                            image[i][j] = 255.0;
                        else
                            image[i][j] = 0.0;
                    }else {
                        image[i][j] = 0.0;
                    }
                }
            }
        }
        return image;
    }

    private double[] findThresholds(double[][] image) {

        return null;
    }


}
