package ar.edu.itba.models;

import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class RGBImageMatrix extends ImageMatrix{
    private double[][] red;
    private double[][] green;
    private double[][] blue;

    protected RGBImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), image);
        this.red = new double[this.getWidth()][this.getHeight()];
        this.green = new double[this.getWidth()][this.getHeight()];
        this.blue = new double[this.getWidth()][this.getHeight()];

        System.out.println(image.getColorModel().hasAlpha());
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 3;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.blue[row][col] = ((int) pixels[pixel] & 0xff); // blue
            this.green[row][col] = (((int) pixels[pixel + 1] & 0xff)); // green
            this.red[row][col] = (((int) pixels[pixel + 2] & 0xff)); // red
            row++;
            if (row == this.getWidth()) {
                row = 0;
                col++;
            }
        }
    }

    @Override
    public Pixel getPixelColor(int x, int y) {
        int red = (int)this.red[x][y];
        int green = (int)this.green[x][y];
        int blue = (int)this.blue[x][y];
        return new RGBPixel(x ,y, red, green, blue);
    }

    @Override
    public void setPixel(Pixel pixel) {
        System.out.println("SET PIXEL");
        RGBPixel rgb = (RGBPixel) pixel;
        int x = rgb.getX(); int y = rgb.getY();
        red[x][y] = rgb.getRed();
        green[x][y] = rgb.getGreen();
        blue[x][y] = rgb.getBlue();
        int color = rgb.getRed();
        color = (color << 8) + rgb.getGreen();
        color = (color << 8) + rgb.getBlue();
        System.out.println(Integer.toHexString(color));
        this.getImage().setRGB(x,y,color);
    }

}
