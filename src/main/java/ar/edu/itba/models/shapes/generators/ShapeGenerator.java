package ar.edu.itba.models.shapes.generators;

import ar.edu.itba.models.shapes.Shape;

import java.util.Set;

/**
 * Created by Luis on 21/10/2017.
 */
public abstract class ShapeGenerator{
    public abstract Set<Shape> generateParametricSpace(int maxX, int maxY, double delta);
}
