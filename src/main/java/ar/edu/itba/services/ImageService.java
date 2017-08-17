package ar.edu.itba.services;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.models.ImageMatrix;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageService {

    @Subscribe
    public void readImage(ImageLoaded imageLoaded) throws IOException {
        System.out.println("reading file");
        new ImageMatrix(ImageIO.read(imageLoaded.getImg()));
    }
}
