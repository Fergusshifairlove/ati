package ar.edu.itba.models;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.models.random.RandomUtils;
import ar.edu.itba.models.random.generators.RandomNumberGenerator;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Arrays;

public class GreyImageMatrix extends ImageMatrix {
    private static Integer[] bands = {1};
    private double[][] grey;

    public GreyImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        this.grey = new double[this.width][this.height];
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 1;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.grey[row][col] = ((int) pixels[pixel] & 0xff); // blue
            row++;
            if (row == this.width) {
                row = 0;
                col++;
            }
        }
    }

    public GreyImageMatrix(int width, int height, double[][] grey) {
        super(width, height, BufferedImage.TYPE_BYTE_GRAY);
        this.grey = grey;
    }

    @Override
    protected BufferedImage toBufferedImage(boolean compress) {
        if (compress) {
            this.compress();
        } else {
            this.applyPunctualOperation(-1, this::truncate);
        }
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = image.getRaster();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                raster.setSample(i, j, 0, grey[i][j]);
            }
        }
        return image;
    }

    @Override
    public Pixel getPixelColor(int x, int y) {
        int value = (int) this.grey[x][y];
        return new GreyPixel(x, y, value);
    }

    @Override
    public void setPixel(Pixel pixel) {
        GreyPixel greyPixel = (GreyPixel) pixel;
        int x = greyPixel.getX();
        int y = greyPixel.getY();
        int val = greyPixel.getGrey();
        setPixel(x, y, val);
    }

    private void setPixel(int x, int y, double val) {
        grey[x][y] = val;
    }

    public int getValue(int x, int y) {
        return (int) this.grey[x][y];
    }

    @Override
    public double[][] getBand(int band) {
        switch (band) {
            case 1:
                return grey;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public Iterable<Integer> getBands() {
        return Arrays.asList(bands);
    }

    @Override
    public void setBand(int b, double[][] band) {
        switch (b) {
            case 1:
                this.grey = band;
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public ImageMatrix getSubImage(int row, int col, int width, int height) {
        double[][] subMatrix = new double[width][height];
        for (int i = row; i < row + width; i++) {
            for (int j = col; j < col + height; j++) {
                subMatrix[i-row][j-col] = grey[i][j];
            }
        }
        return new GreyImageMatrix(width, height, subMatrix);
    }

    public Iterable<Double> getGreyBand() {
        return this.getIterableBand(1);
    }

    public double[][] getGrey() {
        return grey;
    }


    public static ImageMatrix getNoiseImage(int width, int height, RandomNumberGenerator generator, NoiseType type, double percentage) {
        return new GreyImageMatrix(width, height, RandomUtils.getNoiseBand(width, height, generator, type, percentage));
    }

}
