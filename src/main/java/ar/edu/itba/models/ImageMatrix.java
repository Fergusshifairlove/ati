package ar.edu.itba.models;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;

public abstract class ImageMatrix {
    private int height;
    private int width;
    private BufferedImage image;

    public ImageMatrix(int width, int height, BufferedImage image) {
        this.height = height;
        this.width = width;
        this.image = image;
    }

    public static ImageMatrix readImage(BufferedImage image) {
        if (image.getType() == BufferedImage.TYPE_BYTE_GRAY)
            return new GreyImageMatrix(image);
        else if (image.getType() == BufferedImage.TYPE_INT_RGB)
            return new RGBImageMatrix(image);
        else if (image.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            return new RGBImageMatrix(image);
        }
        throw new RuntimeException();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public BufferedImage getImage() { return image; }

    public abstract Pixel getPixelColor(int x, int y);

    public abstract void setPixel(Pixel pixel);

    public abstract ImageMatrix applyPunctualOperation(ToDoubleFunction<Double> operation);

    public abstract ImageMatrix applyBinaryOperation(BinaryOperator<Double> operator, ImageMatrix matrix);

    public BufferedImage deepCopy() {
        ColorModel cm = this.image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = this.image.copyData(null);
        //NOT CROPPED
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
