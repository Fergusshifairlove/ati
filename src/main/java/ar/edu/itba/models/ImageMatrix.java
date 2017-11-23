package ar.edu.itba.models;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.models.random.RandomUtils;
import ar.edu.itba.models.random.generators.RandomNumberGenerator;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;

public abstract class ImageMatrix {
    protected int height;
    protected int width;
    private int type;

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
        } else if (image.getType() == BufferedImage.TYPE_4BYTE_ABGR) {
            return new BGRImagerMatrix(image);
        }
        throw new IllegalArgumentException();
    }



    public int getType() {
        return type;
    }

    public int getHeight() {
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

    public Pixel getPixelColor(Position pos) {
        return this.getPixelColor(pos.getX(), pos.getY());
    }
    public abstract void setPixel(Pixel pixel);

    public ImageMatrix compress() {
        for (Integer b : this.getBands()) {
            double[][] band = this.getBand(b);
            double min = band[0][0];
            double max = min;
            double val;
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    val = band[i][j];
                    if (val < min)
                        min = val;
                    if (val > max)
                        max = val;
                }
            }
            this.dynamicRange(b, max, min);
        }
        return this;
    }

    void dynamicRange(int band, double maxValue, double minValue) {
        this.applyPunctualOperation(band, pixel -> pixel - minValue);
        double c = 255 / (Math.log(1 + (maxValue - minValue)));
        this.applyPunctualOperation(band, pixel -> c * Math.log(1 + pixel));
    }

    double truncate(double p) {
        if (p < 0)
            return 0;
        if (p > 255)
            return 255;
        return p;
    }

    public ImageMatrix applyNoise(int band, NoiseType noiseType, RandomNumberGenerator generator, double percentage) {
        if (band == -1) {
            return this.applyAllBands((b) -> this.applyNoise(b, noiseType, generator, percentage));
        }
        return this.applyBinaryOperation(band, noiseType.getOperator(),
                RandomUtils.getNoiseBand(this.width, this.height, generator, noiseType, percentage));
    }

    public ImageMatrix applyNoise(NoiseType noiseType, RandomNumberGenerator generator, double percentage) {
        return this.applyNoise(-1, noiseType, generator, percentage);
    }
    public Iterable<Double> getIterableBand(int band) {
        double[][] b = this.getBand(band);
        List<Double> itBand = new ArrayList<>();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                itBand.add(b[i][j]);
            }
        }
        return itBand;
    }

    public abstract double[][] getBand(int band);

    public abstract List<Integer> getBands();

    public abstract void setBand(int b, double[][] band);

    public ImageMatrix equalize(Integer band) {
        if (band == -1) {
            return this.applyAllBands(this::equalize);
        }
        Histogram h;
        h = new Histogram(this.getIterableBand(band));
        this.applyPunctualOperation(band, h::equalize);
        return this;
    }

    public ImageMatrix equalize() {
        return this.equalize(-1);
    }

    public ImageMatrix applyFilterOperation(int band, Function<double[][], double[][]> filter) {
        if (band == -1) {
            return this.applyAllBands((b) -> applyFilterOperation(b, filter));
        }
        this.setBand(band, filter.apply(this.getBand(band)));
        return this;
    }

    public ImageMatrix applyFilterOperation(Function<double[][], double[][]> filter) {
        return this.applyFilterOperation(-1, filter);
    }

    public ImageMatrix applyPunctualOperation(int band, ToDoubleFunction<Double> punctualOperation) {
        if (band == -1) {
            return this.applyAllBands((b) -> applyPunctualOperation(b, punctualOperation));
        }
        double[][] b = this.getBand(band);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                b[i][j] = punctualOperation.applyAsDouble(b[i][j]);
            }
        }
        this.setBand(band, b);
        return this;
    }

    public ImageMatrix applyPunctualOperation(ToDoubleFunction<Double> punctualOperation) {
        return this.applyPunctualOperation(-1, punctualOperation);
    }

    public ImageMatrix applyBinaryOperation(Integer band, BinaryOperator<Double> operator, double[][] band2) {
        if (band == -1) {
            return applyAllBands((b) -> applyBinaryOperation(b, operator, band2));
        }
        double[][] band1 = this.getBand(band);

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                band1[i][j] = operator.apply(band1[i][j], band2[i][j]);
            }
        }
        this.setBand(band, band1);
        return this;
    }

    public ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix) {
        if (this.type != matrix.getType()) {
            throw new IllegalArgumentException();
        }
        for (Integer band: matrix.getBands()) {
            this.applyBinaryOperation(band, operator, matrix.getBand(band));
        }
        return this;
    }

    private ImageMatrix applyAllBands(Function<Integer, ImageMatrix> bandOperation) {
        this.getBands().parallelStream().forEach(bandOperation::apply);
        return this;
    }

    public abstract ImageMatrix getSubImage(int row, int col, int width, int height);
}
