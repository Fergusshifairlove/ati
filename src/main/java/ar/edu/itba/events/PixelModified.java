package ar.edu.itba.events;

import ar.edu.itba.models.Pixel;

/**
 * Created by Luis on 18/8/2017.
 */
public class PixelModified {
    private Pixel pixel;

    public PixelModified(Pixel pixel) {
        this.pixel = pixel;
    }

    public Pixel getPixel() {
        return pixel;
    }
}
