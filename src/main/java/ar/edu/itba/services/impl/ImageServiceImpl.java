package ar.edu.itba.services.impl;

import ar.edu.itba.models.Pixel;
import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.services.ImageService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageServiceImpl implements ImageService{

    private ImageMatrix matrix;


    public void loadImage(ImageLoaded imageLoaded) throws IOException {
        System.out.println("reading file");
        this.matrix = ImageMatrix.readImage(ImageIO.read(imageLoaded.getImg()));
        System.out.println("height:" + imageLoaded.getHeight() + " width:" + imageLoaded.getWidth());
        System.out.println("height:" + this.matrix.getHeight() + " width:" + this.matrix.getWidth());
    }

    public Pixel selectPixel(int x, int y) {
        return this.matrix.getPixelColor(x, y);
    }

    @Override
    public void modifyPixel(Pixel pixel) {
        matrix.setPixel(pixel);
    }

    @Override
    public BufferedImage getImage() {
        return matrix.getImage();
    }

}
