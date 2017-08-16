package ar.edu.itba;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Pixel {
    private Color color;
    private int x;
    private int y;
    private Image image;

    public Pixel(Color color, int x, int y, Image image) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }
}
