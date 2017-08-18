package ar.edu.itba.models;

import java.awt.image.BufferedImage;

public abstract class ImageMatrix {
    private int height;
    private int width;
    private BufferedImage image;

    public ImageMatrix(int width, int height, BufferedImage image) {
        this.height = height;
        this.width = width;
        this.image = image;
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

    public BufferedImage getImage() { return image; }

    public abstract Pixel getPixelColor(int x, int y);

    public abstract void setPixel(Pixel pixel);

}
