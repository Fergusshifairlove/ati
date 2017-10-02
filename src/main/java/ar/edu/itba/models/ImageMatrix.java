package ar.edu.itba.models;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.models.masks.DirectionalMask;
import ar.edu.itba.models.masks.Mask;
import ar.edu.itba.models.randomGenerators.RandomNumberGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;

public abstract class ImageMatrix{
    protected int height;
    protected int width;
    private int type;

    public int getType() {
        return type;
    }

    ImageMatrix(int width, int height, int type) {
        this.height = height;
        this.width = width;
        this.type = type;

    }

    public static ImageMatrix readImage(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY)
            return new GreyImageMatrix(image);
        else if (image.getType() == BufferedImage.TYPE_INT_RGB)
            return new RGBImageMatrix(image);
        else if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            return new RGBImageMatrix(image);
        }
        System.out.println(image.getType());
        throw new RuntimeException();
    }

    int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public BufferedImage getImage(boolean compress) {
        return this.toBufferedImage(compress);
    }

    protected abstract BufferedImage toBufferedImage(boolean compress);

    public abstract Pixel getPixelColor(int x, int y);

    public abstract void setPixel(Pixel pixel);

    public abstract ImageMatrix applyPunctualOperation(ToDoubleFunction<Double> operation);

    public abstract ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix);

    public abstract void compress();

    double truncate(double p) {
        if (p < 0)
            return 0;
        if (p > 255)
            return  255;
        return p;
    }

    public abstract void applyNoise(NoiseType noiseType, RandomNumberGenerator generator, double percentage);


    public abstract ImageMatrix applyMask(Mask mask);

    public abstract ImageMatrix applyBorder(DirectionalMask dirMask);

    static double[][] getRandomMatrix(int width, int height, NoiseType noiseType, Iterable<Point> toModify, Iterator<Double> generator) {
        double[][] noise = new double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = noiseType.getNeutral();
            }
        }

        for (Point p: toModify) {
            noise[p.x][p.y] = generator.next();
        }

        return noise;
    }

    static Iterable<Point> getPixelsToModify(int width, int height, long cant) {
        Set<Point> modified = new HashSet<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Iterator<Integer> cols = random.ints(0, height).iterator();
        Iterator<Integer> rows = random.ints(0, width).iterator();

        do {
            modified.add(new Point(rows.next(), cols.next()));
        }while (modified.size() < cant);

        return modified;
    }

    protected Iterable<Double> getBand(double[][] band) {
        List<Double> b = new ArrayList<>();
        for (int i=0; i<this.width; i++) {
            for (int j=0; j<this.height; j++) {
                b.add(band[i][j]);
            }
        }
        return b;
    }

    public Iterable<Double> getItBand(int band) {
        return this.getBand(this.getBand(band));
    }

    public void applyBandOperation(int band, ToDoubleFunction<Double> operation) {
        if (band == -1) {
            this.applyPunctualOperation(operation);
            return;
        }
        this.applyBandOperation(this.getBand(band), operation);
    }

    protected abstract double[][] getBand(int band);
    public abstract Iterable<Integer> getBands();

    private void applyBandOperation(double[][] band, ToDoubleFunction<Double> operation) {
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                band[i][j] = operation.applyAsDouble(band[i][j]);
            }
        }
    }

    public abstract void equalize();
}
