package ar.edu.itba.events;

import java.io.File;

public class ImageLoaded {
    private File img;
    private int width;
    private int height;
    private boolean rgb;

    public ImageLoaded(File img, int width, int height, boolean rgb) {
        this.img = img;
        this.width = width;
        this.height = height;
        this.rgb = rgb;
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

    public boolean isRgb() {
        return rgb;
    }
}
