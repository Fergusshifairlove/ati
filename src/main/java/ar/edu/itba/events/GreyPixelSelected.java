package ar.edu.itba.events;

import ar.edu.itba.models.GreyPixel;

/**
 * Created by Luis on 18/8/2017.
 */
public class GreyPixelSelected extends PixelSelected{

    public GreyPixelSelected(GreyPixel pixel) {
        super(pixel);
    }

    public GreyPixel getPixel() {
        return (GreyPixel) super.getPixel();
    }
}
