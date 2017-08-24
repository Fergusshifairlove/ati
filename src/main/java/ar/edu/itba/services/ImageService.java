package ar.edu.itba.services;

import ar.edu.itba.models.ImageMatrix;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageService {
    ImageMatrix loadImage(File img) throws IOException;
    BufferedImage deepCopy(BufferedImage image);
}
