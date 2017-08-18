package ar.edu.itba.services;

import ar.edu.itba.models.Pixel;
import ar.edu.itba.events.ImageLoaded;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface ImageService {
    void loadImage(ImageLoaded loaded) throws IOException;
    Pixel selectPixel(int x, int y);

    void modifyPixel(Pixel pixel);

    BufferedImage getImage();
}
