package ar.edu.itba.models;

import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;

public class GreyImageMatrix extends ImageMatrix{
    private double[][] grey;

    public GreyImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), image);
        System.out.println("GREY");
        this.grey = new double[this.getWidth()][this.getHeight()];
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 1;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.grey[row][col] = ((int) pixels[pixel] & 0xff); // blue
            row++;
            if (row == this.getWidth()) {
                row = 0;
                col++;
            }
        }
    }

    @Override
    public Pixel getPixelColor(int x, int y) {
        int value = (int) this.grey[x][y];
        return new GreyPixel(x, y, value);
    }

    @Override
    public void setPixel(Pixel pixel) {
        System.out.println("SET PIXEL");
        GreyPixel greyPixel = (GreyPixel) pixel;
        int x = greyPixel.getX(); int y = greyPixel.getY();
        int val = greyPixel.getGrey();
        setPixel(x, y, val);
    }

    private void setPixel(int x, int y, double val) {
        grey[x][y] = val;
        double [] array = {val};
        this.getImage().getRaster().setPixel(x,y,array);
    }

    @Override
    public ImageMatrix applyPunctualOperation(ToDoubleFunction<Double> operation) {
        double val;
        double[] array = new double[1];
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                val = operation.applyAsDouble(grey[i][j]);
                setPixel(i, j, val);
            }
        }
        return this;
    }

    @Override
    public ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix) {
        double val;
        GreyImageMatrix greyMatrix = (GreyImageMatrix) matrix;
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                val = operator.apply(grey[i][j], greyMatrix.grey[i][j]);
                setPixel(i, j, val);
            }
        }
        return this;
    }

}
