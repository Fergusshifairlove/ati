package ar.edu.itba.events;

import ar.edu.itba.models.RGBPixel;

/**
 * Created by Luis on 18/8/2017.
 */
public class RGBPixelModified extends PixelModified{

    public RGBPixelModified(RGBPixel pixel) {
        super(pixel);
    }

    public RGBPixel getPixel() {
        return (RGBPixel) super.getPixel();
    }
}
