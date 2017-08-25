package ar.edu.itba.events;

import ar.edu.itba.models.ImageMatrix;

public class ImageLoaded {
    private ImageMatrix image;

    public ImageLoaded(ImageMatrix image) {
        this.image = image;
    }

    public ImageMatrix getImage() {
        return image;
    }
}
