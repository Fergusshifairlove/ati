package ar.edu.itba.models;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.ToDoubleFunction;

public class GreyImageMatrix extends ImageMatrix implements Iterable<GreyPixel>{
    private double[][] grey;

    public GreyImageMatrix(BufferedImage image) {
        super(image.getWidth(), image.getHeight());
        this.grey = new double[this.getWidth()][this.getHeight()];
        byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int pixelLength = 1;
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
            this.grey[row][col] = ((int) pixels[pixel] & 0xff); // blue
            row++;
            if (row == this.getWidth()) {
                row = 0;
                col++;
            }
        }
    }

    @Override
    protected BufferedImage toBufferedImage(boolean compress) {
        BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = image.getRaster();
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                raster.setSample(i, j, 0, grey[i][j]);
            }
        }
        return image;
    }

    @Override
    public Pixel getPixelColor(int x, int y) {
        int value = (int) this.grey[x][y];
        return new GreyPixel(x, y, value);
    }

    @Override
    public void setPixel(Pixel pixel) {
        GreyPixel greyPixel = (GreyPixel) pixel;
        int x = greyPixel.getX(); int y = greyPixel.getY();
        int val = greyPixel.getGrey();
        setPixel(x, y, val);
    }

    private void setPixel(int x, int y, double val) {
        grey[x][y] = val;
    }

    @Override
    public ImageMatrix applyPunctualOperation(ToDoubleFunction<Double> operation) {
        double val;
        for (int i = 0; i < this.getWidth(); i++) {
            for (int j = 0; j < this.getHeight(); j++) {
                val = operation.applyAsDouble(grey[i][j]);
                setPixel(i, j, val);
            }
        }
        return this;
    }

    @Override
    public ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix) {
        double val;
        GreyImageMatrix greyMatrix = (GreyImageMatrix) matrix;
        for (int i = 0; i < this.getWidth() && i < matrix.getWidth(); i++) {
            for (int j = 0; j < this.getHeight() && i < matrix.getHeight(); j++) {
                val = operator.apply(grey[i][j], greyMatrix.grey[i][j]);
                this.setPixel(i, j, val);
            }
        }
        return this;
    }

    @Override
    public Iterator<GreyPixel> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super GreyPixel> action) {

    }

    @Override
    public Spliterator<GreyPixel> spliterator() {
        return null;
    }
}
