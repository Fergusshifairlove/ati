package ar.edu.itba.events;

import java.io.File;

/**
 * Created by Luis on 20/8/2017.
 */
public class CompareImages {
    private File image;

    public CompareImages(File image) {
        this.image = image;
    }

    public File getImage() {
        return image;
    }
}
