package ar.edu.itba.models;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class BGRImagerMatrix extends RGBImageMatrix{
    public BGRImagerMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        this.red = new double[this.getWidth()][this.getHeight()];
        this.green = new double[this.getWidth()][this.getHeight()];
        this.blue = new double[this.getWidth()][this.getHeight()];
        this.ByteImage(image);
    }

    private void ByteImage(BufferedImage image) {
        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 4;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.blue[row][col] = ((int) pixels[pixel + 1] & 0xff); // blue
            this.green[row][col] = (((int) pixels[pixel + 2] & 0xff)); // green
            this.red[row][col] = (((int) pixels[pixel + 3] & 0xff)); // red
            row++;
            if (row == this.getWidth()) {
                row = 0;
                col++;
            }
        }
    }
}
