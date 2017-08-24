package ar.edu.itba.models;


import java.util.regex.Pattern;

public abstract class Pixel {
    private int x;
    private int y;
    private static Pattern val = Pattern.compile("[0-255]");

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract String toString();

}
