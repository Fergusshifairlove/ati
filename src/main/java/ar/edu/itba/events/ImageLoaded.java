package ar.edu.itba.events;

import java.io.File;

public class ImageLoaded {
    private int type;

    public ImageLoaded(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
