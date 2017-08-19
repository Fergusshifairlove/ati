package ar.edu.itba.models;

import com.sun.javaws.exceptions.InvalidArgumentException;

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
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY)
            return new GreyImageMatrix(image);
        else if (image.getType() == BufferedImage.TYPE_INT_RGB)
            return new RGBImageMatrix(image);

        throw new RuntimeException();
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
