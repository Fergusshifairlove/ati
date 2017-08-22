package ar.edu.itba.events;

import java.io.File;

/**
 * Created by Luis on 20/8/2017.
 */
public class OpenImage {
    private File image;

    public OpenImage(File image) {
        this.image = image;
    }

    public File getImage() {
        return image;
    }
}
