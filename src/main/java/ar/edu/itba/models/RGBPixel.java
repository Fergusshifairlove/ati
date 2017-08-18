package ar.edu.itba.models;

/**
 * Created by Luis on 17/8/2017.
 */
public class RGBPixel extends Pixel {
    private int red, green, blue;

    public RGBPixel(int x, int y, int red, int green, int blue) {
        super(x, y);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return "(" + red + "," + green + "," + blue + ")";
    }
}
