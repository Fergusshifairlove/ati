package ar.edu.itba.events;

import ar.edu.itba.models.GreyPixel;

/**
 * Created by Luis on 18/8/2017.
 */
public class GreyPixelModified extends PixelModified{
    public GreyPixelModified(GreyPixel pixel) {
        super(pixel);
    }

    public GreyPixel getPixel() {
        return (GreyPixel) super.getPixel();
    }
}
