package ar.edu.itba.events;

import java.io.InputStream;

public class ImageLoaded {
    private InputStream img;
    private int width;
    private int height;

    public ImageLoaded(InputStream img, int width, int height) {
        this.img = img;
        this.width = width;
        this.height = height;
    }

    public InputStream getImg() {
        return img;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
