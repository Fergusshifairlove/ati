package ar.edu.itba.events;

import java.io.File;

/**
 * Created by Luis on 14/8/2017.
 */
public class SaveImage {
    private File img;

    public SaveImage(File img) {
        this.img = img;
    }

    public File getImg() {
        return img;
    }
}
