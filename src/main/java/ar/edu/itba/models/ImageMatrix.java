package ar.edu.itba.models;

import java.awt.image.BufferedImage;

public class ImageMatrix {
    private double [][] matrix;

    public ImageMatrix(int width, int height) {
        this.matrix = new double[height][width];
    }

    public double getValue(int x,int y) {
        return this.matrix[y][x];
    }

    public void setValue(int x, int y, double value) {
        this.matrix[y][x] = value;
    }

    public BufferedImage toBufferedImage() {
        return null;
    }

}
