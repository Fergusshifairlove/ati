package ar.edu.itba.models;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;

public class RGBImageMatrix extends ImageMatrix {
    private static Integer[] bands = {1, 2, 3};
    private double[][] red;
    private double[][] green;
    private double[][] blue;

    protected RGBImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), image.getType());
        this.red = new double[this.getWidth()][this.getHeight()];
        this.green = new double[this.getWidth()][this.getHeight()];
        this.blue = new double[this.getWidth()][this.getHeight()];
        if (this.getType() == BufferedImage.TYPE_INT_RGB) {
            this.IntRGB(image);
        } else if (this.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            this.ByteImage(image);
        }

    }

    public RGBImageMatrix(int width, int height, double[][] red, double[][] green, double[][] blue, int type) {
        super(width, height, type);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private void IntRGB(BufferedImage image) {
        final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length - 2; pixel += pixelLength) {
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

    private void ByteImage(BufferedImage image) {
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
        int red = (int) this.red[x][y];
        int green = (int) this.green[x][y];
        int blue = (int) this.blue[x][y];
        return new RGBPixel(x, y, red, green, blue);
    }

    @Override
    public void setPixel(Pixel pixel) {
        RGBPixel rgb = (RGBPixel) pixel;
        int x = rgb.getX();
        int y = rgb.getY();
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
        return this;
    }

    @Override
    public double[][] getBand(int band) {
        switch (band) {
            case 1:
                return red;
            case 2:
                return green;
            case 3:
                return blue;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Integer> getBands() {
        return Arrays.asList(bands);
    }

    @Override
    public void setBand(int b, double[][] band) {
        switch (b) {
            case 1:
                this.red = band;
                break;
            case 2:
                this.green = band;
                break;
            case 3:
                this.blue = band;
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected BufferedImage toBufferedImage(boolean compress) {
        if (compress) {
            this.compress();
        } else {
            this.applyPunctualOperation(this::truncate);
        }
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), this.getType());
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

    public Iterable<Double> getRedBand() {
        return this.getIterableBand(1);
    }

    public Iterable<Double> getGreenBand() {
        return this.getIterableBand(2);
    }

    public Iterable<Double> getBlueBand() {
        return this.getIterableBand(3);
    }

    @Override
    public ImageMatrix getSubImage(int row, int col, int width, int height) {
        double[][] subR = new double[width][height];
        double[][] subG = new double[width][height];
        double[][] subB = new double[width][height];
        for (int i = row; i < row + width; i++) {
            for (int j = col; j < col + height; j++) {
                subR[i-row][j-col] = red[i][j];
                subG[i-row][j-col] = green[i][j];
                subB[i-row][j-col] = blue[i][j];
            }
        }
        return new RGBImageMatrix(width, height, subR, subG, subB, this.getType());
    }
}
