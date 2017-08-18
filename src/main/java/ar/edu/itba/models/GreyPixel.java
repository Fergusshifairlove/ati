package ar.edu.itba.models;

/**
 * Created by Luis on 17/8/2017.
 */
public class GreyPixel extends Pixel {
    private int grey;

    public GreyPixel(int x, int y, int grey) {
        super(x, y);
        this.grey = grey;
    }

    public int getGrey() {
        return grey;
    }

    @Override
    public String toString() {
        return "" + grey;
    }
}
