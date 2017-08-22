package ar.edu.itba.services;

import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.models.Pixel;
import ar.edu.itba.events.ImageLoaded;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageService {
    ImageMatrix loadImage(File img) throws IOException;
}
