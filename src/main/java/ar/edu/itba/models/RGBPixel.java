package ar.edu.itba.models;

import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;

/**
 * Created by Luis on 17/8/2017.
 */
public class RGBPixel extends Pixel {
    private double red, green, blue;

    public RGBPixel(int x, int y, double red, double green, double blue) {
        super(x, y);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public double getRed() {
        return red;
    }

    public double getGreen() {
        return green;
    }

    public double getBlue() {
        return blue;
    }

    @Override
    protected Pixel copy() {
        return new RGBPixel(0, 0, red, green, blue);
    }

    @Override
    public String toString() {
        return "(" + red + "," + green + "," + blue + ")";
    }

    @Override
    public Pixel binaryOperation(Pixel pixel, BinaryOperator<Double> binaryOperator) {
        RGBPixel other = (RGBPixel) pixel;
        return new RGBPixel(this.getX(), this.getY(),
                binaryOperator.apply(red, other.red),
                binaryOperator.apply(blue, other.blue),
                binaryOperator.apply(green, other.green));
    }

    @Override
    public Pixel singleOperation(ToDoubleFunction<Double> operation) {
        return new RGBPixel(this.getX(), this.getY(),
                operation.applyAsDouble(red),
                operation.applyAsDouble(green),
                operation.applyAsDouble(blue));
    }

    @Override
    public double norm() {
        return Math.sqrt(Math.pow(red, 2) + Math.pow(blue, 2) + Math.pow(green, 2));
    }
}
