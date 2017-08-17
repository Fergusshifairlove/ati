package ar.edu.itba.models;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferDouble;

public class ImageMatrix {
    private double [][] matrix;
    private int height;
    private int width;

    public ImageMatrix(int width, int height) {
        this.matrix = new double[height][width];
    }

    public ImageMatrix(BufferedImage image) {
        this.height = image.getHeight();
        this.width = image.getWidth();

        System.out.println(this.width + "x" + this.height);
        byte[] buffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.out.println(buffer.length + " " + height * width);
        this.matrix = new double[height][width];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                matrix[j][i] = image.getRaster().getDataBuffer().getElemDouble(i*this.height + j);
            }
        }
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
