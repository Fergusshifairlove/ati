package ar.edu.itba.events;

import ar.edu.itba.models.Pixel;

/**
 * Created by Luis on 18/8/2017.
 */
public class PixelSelected {
    private Pixel pixel;

    public PixelSelected(Pixel pixel) {
        this.pixel = pixel;
    }

    public Pixel getPixel() {
        return pixel;
    }

    public void setPixel(Pixel pixel) {
        this.pixel = pixel;
    }
}
