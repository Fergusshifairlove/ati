package ar.edu.itba.models;

import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Arrays;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

public class RGBImageMatrix extends ImageMatrix{
    private double[][] red;
    private double[][] green;
    private double[][] blue;

    protected RGBImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), image);
        this.red = new double[this.getWidth()][this.getHeight()];
        this.green = new double[this.getWidth()][this.getHeight()];
        this.blue = new double[this.getWidth()][this.getHeight()];

        System.out.println(image.getColorModel().hasAlpha());
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.blue[row][col] = ((int) pixels[pixel] & 0xff); // blue
            this.green[row][col] = (((int) pixels[pixel + 1] & 0xff)); // green
            this.red[row][col] = (((int) pixels[pixel + 2] & 0xff)); // red
            row++;
            if (row == this.getWidth()) {
                row = 0;
                col++;
            }
        }
    }

    @Override
    public Pixel getPixelColor(int x, int y) {
        int red = (int)this.red[x][y];
        int green = (int)this.green[x][y];
        int blue = (int)this.blue[x][y];
        return new RGBPixel(x ,y, red, green, blue);
    }

    @Override
    public void setPixel(Pixel pixel) {
        System.out.println("SET PIXEL");
        RGBPixel rgb = (RGBPixel) pixel;
        int x = rgb.getX(); int y = rgb.getY();
        this.setPixel(x, y, rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    private void setPixel(int x, int y, double r, double g, double b) {
        red[x][y] = r;
        green[x][y] = g;
        blue[x][y] = b;
        int color = (int) r;
        color = (color << 8) + (int) g;
        color = (color << 8) + (int) b;
        this.getImage().setRGB(x,y,color);
    }

    @Override
    public ImageMatrix applyPunctualOperation(ToDoubleFunction<Double> operation) {
        double r, g, b;
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                r = operation.applyAsDouble(red[i][j]);
                g = operation.applyAsDouble(green[i][j]);
                b = operation.applyAsDouble(blue[i][j]);
                this.setPixel(i, j, r, g, b);
            }
        }
        return this;
    }

    @Override
    public ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix) {
        double r, g, b;
        RGBImageMatrix rgbMatrix = (RGBImageMatrix) matrix;
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                r = operator.apply(red[i][j], rgbMatrix.red[i][j]);
                g = operator.apply(green[i][j], rgbMatrix.green[i][j]);
                b = operator.apply(blue[i][j], rgbMatrix.blue[i][j]);
                this.setPixel(i, j, r, g, b);
            }
        }
        return this;
    }

}
