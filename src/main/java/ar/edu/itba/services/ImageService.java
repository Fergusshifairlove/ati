package ar.edu.itba.services;

import ar.edu.itba.Pixel;
import ar.edu.itba.events.ImageLoaded;

import java.awt.*;
import java.io.IOException;

public interface ImageService {
    void loadImage(ImageLoaded loaded) throws IOException;
    Pixel selectPixel(int x, int y);
}
