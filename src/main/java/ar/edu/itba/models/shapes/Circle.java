package ar.edu.itba.models.shapes;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luis on 20/10/2017.
 */
public class Circle extends Shape{

    private double radius, centerX, centerY;

    public Circle(double delta, double radius, double centerX, double centerY) {
        super(delta);
        this.radius = radius;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    @Override
    public boolean belongs(int x, int y) {
        return Math.abs(Math.pow(radius,2) - Math.pow(x - centerX,2) - Math.pow(y - centerY,2)) <= delta;
    }

    @Override
    public javafx.scene.shape.Shape toFxShape(Bounds bounds) {
        javafx.scene.shape.Circle c = new javafx.scene.shape.Circle(centerX, centerY, radius);
        c.setFill(Color.TRANSPARENT);
        c.setStroke(Color.YELLOW);
        c.setStrokeWidth(2);
        return c;
    }

}
