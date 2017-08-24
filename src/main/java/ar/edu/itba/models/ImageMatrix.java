package ar.edu.itba.models;

import java.awt.image.BufferedImage;
import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;

public abstract class ImageMatrix{
    private int height;
    private int width;
    private double maxValue = 255;
    private double minValue = 0;

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public ImageMatrix(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public static ImageMatrix readImage(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY)
            return new GreyImageMatrix(image);
        else if (image.getType() == BufferedImage.TYPE_INT_RGB)
            return new RGBImageMatrix(image);
        else if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            return new RGBImageMatrix(image);
        }
        throw new RuntimeException();
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

    public abstract void setPixel(Pixel pixel);

    public abstract ImageMatrix applyPunctualOperation(ToDoubleFunction<Double> operation);

    public abstract ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix);

    public void dynamicRange(double maxValue, double minValue) {
        this.applyPunctualOperation(pixel -> pixel - minValue);
        double c = 255/(Math.log(1 + (maxValue -minValue)));
        this.applyPunctualOperation(pixel -> c * Math.log(1 + pixel));
    }

    protected double truncate(double p) {
        if (p < 0)
            return p;
        if (p > 255)
            return  255;
        return p;
    }
}
