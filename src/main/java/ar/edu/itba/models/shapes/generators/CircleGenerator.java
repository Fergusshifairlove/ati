package ar.edu.itba.models.shapes.generators;

import ar.edu.itba.models.shapes.Circle;
import ar.edu.itba.models.shapes.Shape;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luis on 21/10/2017.
 */
public class CircleGenerator extends ShapeGenerator{
    @Override
    public Set<Shape> generateParametricSpace(int maxX, int maxY, double delta) {
        Set<Shape> parametricSpace = new HashSet<>();
        for (double radius = Math.sqrt(2); radius <= Math.sqrt(2) * Math.max(maxX, maxY)/2; radius += 10 * Math.sqrt(2)) {
            for (int x = 0; x <= maxX; x+=5) {
                for (int y = 0; y <= maxY; y+=5) {
                    parametricSpace.add(new Circle(delta, radius, x, y));
                }

            }
        }
        return parametricSpace;
    }

    @Override
    public String toString() {
        return "Circle";
    }
}
