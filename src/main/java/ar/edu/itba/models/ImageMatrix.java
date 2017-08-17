package ar.edu.itba.models;

import ar.edu.itba.Pixel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferDouble;

public abstract class ImageMatrix {
    private int height;
    private int width;

    public ImageMatrix(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public static ImageMatrix readImage(BufferedImage image) {
        if (image.getRaster().getNumDataElements() == 1)
            return new GreyImageMatrix(image);
        else
            return new RGBImageMatrix(image);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public abstract Pixel getPixelColor(int x, int y);
}
