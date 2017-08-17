package ar.edu.itba.models;

import ar.edu.itba.Pixel;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class GreyImageMatrix extends ImageMatrix{
    private double[][] grey;

    public GreyImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.grey = new double[this.getWidth()][this.getHeight()];
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 1;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.grey[row][col] = ((int) pixels[pixel] & 0xff); // blue
            col++;
            if (col == this.getHeight()) {
                col = 0;
                row++;
            }
        }
    }

    @Override
    public Pixel getPixelColor(int x, int y) {
        int value = (int) this.grey[x][y];
        return new Pixel(Color.grayRgb(value), x, y);
    }
}
