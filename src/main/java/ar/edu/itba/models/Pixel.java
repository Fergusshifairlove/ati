package ar.edu.itba.models;


import java.util.function.BinaryOperator;
import java.util.function.ToDoubleFunction;
import java.util.regex.Pattern;

public abstract class Pixel {
    private Position position;

    public Pixel(int x, int y) {
        this.position = new Position(x,y);
    }

    public int getX() {
        return position.getX();
    }

    public int getY() {
        return position.getY();
    }

    public Pixel changePosition(Position position) {
        Pixel p = this.copy();
        p.position = position;
        return p;
    }

    protected abstract Pixel copy();

    public Position getPosition() {
        return position;
    }

    public abstract String toString();

    public abstract Pixel binaryOperation(Pixel pixel, BinaryOperator<Double> binaryOperator);

    public abstract Pixel singleOperation(ToDoubleFunction<Double> operation);

    public abstract double norm();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pixel pixel = (Pixel) o;

        return position != null ? position.equals(pixel.position) : pixel.position == null;
    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }
}
