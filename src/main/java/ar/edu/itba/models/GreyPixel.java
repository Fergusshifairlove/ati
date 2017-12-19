package ar.edu.itba.models;

import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;

/**
 * Created by Luis on 17/8/2017.
 */
public class GreyPixel extends Pixel {
    private double grey;

    public GreyPixel(int x, int y, double grey) {
        super(x, y);
        this.grey = grey;
    }

    public double getGrey() {
        return grey;
    }


    @Override
    protected Pixel copy() {
        return new GreyPixel(0 ,0, grey);
    }

    @Override
    public String toString() {
        return "" + grey;
    }

    @Override
    public Pixel binaryOperation(Pixel pixel, BinaryOperator<Double> binaryOperator) {
        GreyPixel other = (GreyPixel) pixel;
        return new GreyPixel(this.getX(), this.getY(),
                binaryOperator.apply(grey, other.grey));
    }

    @Override
    public Pixel singleOperation(ToDoubleFunction<Double> operation) {
        return new GreyPixel(this.getX(), this.getY(), operation.applyAsDouble(grey));
    }

    @Override
    public double norm() {
        return Math.sqrt(Math.pow(grey, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        GreyPixel greyPixel = (GreyPixel) o;

        return Double.compare(greyPixel.grey, grey) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(grey);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
