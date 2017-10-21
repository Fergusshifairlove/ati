package ar.edu.itba.models.shapes;

import javafx.geometry.Bounds;

/**
 * Created by Luis on 20/10/2017.
 */
public abstract class Shape {
    protected double delta;

    protected Shape(double delta) {
        this.delta = delta;
    }
    public abstract boolean belongs(int x, int y);

    public abstract javafx.scene.shape.Shape toFxShape(Bounds bounds);
}
