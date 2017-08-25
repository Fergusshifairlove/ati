package ar.edu.itba.events;

import ar.edu.itba.models.ImageMatrix;

/**
 * Created by Luis on 20/8/2017.
 */
public class ImageModified {
    private ImageMatrix modified;

    public ImageModified(ImageMatrix modified) {
        this.modified = modified;
    }

    public ImageMatrix getModified() {
        return modified;
    }
}
