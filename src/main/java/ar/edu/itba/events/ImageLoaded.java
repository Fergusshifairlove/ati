package ar.edu.itba.events;

import java.io.File;

public class ImageLoaded {
    private File img;
    private int width;
    private int height;

    public ImageLoaded(File img, int width, int height) {
        this.img = img;
        this.width = width;
        this.height = height;
    }

    public File getImg() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
