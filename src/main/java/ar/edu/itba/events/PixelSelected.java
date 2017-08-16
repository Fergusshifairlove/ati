package ar.edu.itba.events;

import ar.edu.itba.Pixel;

public class PixelSelected {
    private Pixel pixel;

    public PixelSelected(Pixel pixel) {
        this.pixel = pixel;
    }

    public Pixel getPixel() {
        return pixel;
    }
}
