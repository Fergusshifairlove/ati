package ar.edu.itba.models;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.models.randomGenerators.RandomNumberGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;

public class RGBImageMatrix extends ImageMatrix{
    private double[][] red;
    private double[][] green;
    private double[][] blue;

    protected RGBImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.red = new double[this.getWidth()][this.getHeight()];
        this.green = new double[this.getWidth()][this.getHeight()];
        this.blue = new double[this.getWidth()][this.getHeight()];

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

    public RGBImageMatrix(int width, int height, double[][] red, double[][] green, double[][] blue) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
        this.red = red;
        this.green =green;
        this.blue = blue;
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
        RGBPixel rgb = (RGBPixel) pixel;
        int x = rgb.getX(); int y = rgb.getY();
        this.setPixel(x, y, rgb.getRed(), rgb.getGreen(), rgb.getBlue());
    }

    private void setPixel(int x, int y, double r, double g, double b) {
        red[x][y] = r;
        green[x][y] = g;
        blue[x][y] = b;
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
        this.updateMinMaxValues(operation);
        return this;
    }

    @Override
    public ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix) {
        double r, g, b;
        RGBImageMatrix rgbMatrix = (RGBImageMatrix) matrix;
        for (int i = 0; i < this.getWidth() && i < matrix.getWidth(); i++) {
            for (int j = 0; j < this.getHeight() && j < matrix.getHeight(); j++) {
                r = operator.apply(red[i][j], rgbMatrix.red[i][j]);
                g = operator.apply(green[i][j], rgbMatrix.green[i][j]);
                b = operator.apply(blue[i][j], rgbMatrix.blue[i][j]);
                this.setPixel(i, j, r, g, b);
            }
        }
        this.updateMinMaxValues(operator, matrix);
        return this;
    }

    @Override
    public void applyNoise(NoiseType noiseType, RandomNumberGenerator generator, double percentage) {
        long cant = Math.round(this.width * this.height * percentage);
        DoubleStream randoms;
        Iterable<Point> toModify;

        double[][][] matrices = {new double[this.width][this.height], new double[this.width][this.height], new double[this.width][this.height]};
        for (int i = 0; i < matrices.length; i++) {
            randoms = generator.doubles(cant);
            toModify = getPixelsToModify(this.width, this.height, cant);
            matrices[i] = getRandomMatrix(this.width, this.height, noiseType, toModify, randoms.iterator());
        }
        ImageMatrix noise = new RGBImageMatrix(this.width, this.height, matrices[0], matrices[1], matrices[2]);
        this.applyBinaryOperation((x1, x2) -> x1 + x2, noise);

    }

    public static RGBImageMatrix getNoiseImage(int width, int height, RandomNumberGenerator generator, NoiseType type) {
        long cant = Math.round(width * height);
        DoubleStream randoms;
        Iterable<Point> toModify;

        double[][][] matrices = {new double[width][height], new double[width][height], new double[width][height]};
        for (int i = 0; i < matrices.length; i++) {
            randoms = generator.doubles(cant);
            toModify = getPixelsToModify(width, height, cant);
            matrices[i] = getRandomMatrix(width, height, type, toModify, randoms.iterator());
        }
        return new RGBImageMatrix(width, height, matrices[0], matrices[1], matrices[2]);
    }

    @Override
    protected BufferedImage toBufferedImage(boolean compress) {
        if (compress) {
            this.dynamicRange(getMaxValue(), getMinValue());
        }
        else {
            this.applyPunctualOperation(this::truncate);
        }
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = image.getRaster();
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                raster.setSample(i, j, 0, red[i][j]);
                raster.setSample(i, j, 1, green[i][j]);
                raster.setSample(i, j, 2, blue[i][j]);
            }
        }
        return image;
    }

}
