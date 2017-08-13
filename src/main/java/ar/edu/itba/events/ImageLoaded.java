package ar.edu.itba.events;

import java.io.InputStream;

public class ImageLoaded {
    private InputStream img;

    public ImageLoaded(InputStream img) {
        this.img = img;
    }

    public InputStream getImg() {
        return img;
    }

    public void setImg(InputStream img) {
        this.img = img;
    }
}
