package ar.edu.itba.events;

import ar.edu.itba.models.RGBPixel;

public class RGBPixelSelected extends PixelSelected{

    public RGBPixelSelected(RGBPixel pixel) {
        super(pixel);
    }

    public RGBPixel getPixel() {
        return (RGBPixel) super.getPixel();
    }
}
