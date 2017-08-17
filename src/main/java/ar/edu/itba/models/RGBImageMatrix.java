package ar.edu.itba.models;

import ar.edu.itba.Pixel;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class RGBImageMatrix extends ImageMatrix{
    private double[][] red;
    private double[][] green;
    private double[][] blue;

    protected RGBImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.red = new double[this.getWidth()][this.getHeight()];
        this.green = new double[this.getWidth()][this.getHeight()];
        this.blue = new double[this.getWidth()][this.getHeight()];
        Color color;
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.blue[row][col] = ((int) pixels[pixel] & 0xff); // blue
            this.green[row][col] = (((int) pixels[pixel + 1] & 0xff)); // green
            this.red[row][col] = (((int) pixels[pixel + 2] & 0xff)); // red
            col++;
            if (col == this.getHeight()) {
                col = 0;
                row++;
            }
        }
    }

    @Override
    public Pixel getPixelColor(int x, int y) {
        int red = (int)this.red[x][y];
        int green = (int)this.green[x][y];
        int blue = (int)this.blue[x][y];
        return new Pixel(Color.rgb(red, green, blue),x ,y);
    }
}
