package ar.edu.itba;


import javafx.scene.paint.Color;

public class Pixel {
    private Color color;
    private int x;
    private int y;

    public Pixel(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
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

}
