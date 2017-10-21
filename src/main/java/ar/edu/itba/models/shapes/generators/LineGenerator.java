package ar.edu.itba.models.shapes.generators;

import ar.edu.itba.models.shapes.Line;
import ar.edu.itba.models.shapes.Shape;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luis on 21/10/2017.
 */
public class LineGenerator extends ShapeGenerator{
    @Override
    public Set<Shape> generateParametricSpace(int maxX, int maxY, double delta) {
        Set<Shape> parametricSpace = new HashSet<>();
        int d = Math.max(maxX, maxY);
        for (int tita = -90; tita <= 90; tita+=5) {
            for (double ro = -Math.sqrt(2.0)*d; ro <= Math.sqrt(2.0)*d; ro+= 2*Math.sqrt(2.0)) {
                parametricSpace.add(new Line(delta, tita, ro));
            }
        }
        return parametricSpace;
    }

    @Override
    public String toString() {
        return "Line";
    }
}
