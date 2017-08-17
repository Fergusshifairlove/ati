package ar.edu.itba.services.impl;

import ar.edu.itba.Pixel;
import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.PixelSelected;
import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.services.ImageService;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageServiceImpl implements ImageService{

    private ImageMatrix matrix;


    public void loadImage(ImageLoaded imageLoaded) throws IOException {
        System.out.println("reading file");
        this.matrix = ImageMatrix.readImage(ImageIO.read(imageLoaded.getImg()));
    }

    public Pixel selectPixel(int x, int y) {
        return this.matrix.getPixelColor(x, y);
    }

}
