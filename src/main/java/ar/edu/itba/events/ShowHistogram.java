package ar.edu.itba.events;

import ar.edu.itba.models.ImageMatrix;

public class ShowHistogram {
    private ImageMatrix image;

    public ShowHistogram(ImageMatrix image) {
        this.image = image;
    }

    public ImageMatrix getImage() {
        return image;
    }
}
