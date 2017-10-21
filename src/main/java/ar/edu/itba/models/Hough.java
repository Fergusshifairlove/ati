package ar.edu.itba.models;

import ar.edu.itba.models.shapes.Shape;
import ar.edu.itba.models.shapes.generators.ShapeGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Luis on 20/10/2017.
 */
public class Hough {

    private ShapeGenerator generator;
    private double threshold;
    private double delta;
    private Set<Shape> parametricSpace = null;

    public Hough(ShapeGenerator generator) {
        this.generator = generator;
    }

    public Hough(ShapeGenerator generator, double threshold) {
        this.generator = generator;
        this.threshold = threshold;
    }

    public Hough(ShapeGenerator generator, double threshold, double delta) {
        this.generator = generator;
        this.threshold = threshold;
        this.delta = delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public void generateParametricSpace(int width, int height) {
        this.parametricSpace = this.generator.generateParametricSpace(width, height, delta);
    }

    public Set<Shape> findShapes(double[][] band) {

        if (this.parametricSpace == null) {
            this.generateParametricSpace(band.length, band[0].length);
        }

        System.out.println("parametric size: " + this.parametricSpace.size());
        Map<Shape, Integer> accumulator = new HashMap<>();
        for (Shape shape: parametricSpace) {
            accumulator.put(shape, 0);
        }

        for (int i = 0; i < band.length; i++) {
            for (int j = 0; j < band[i].length ; j++) {
                if (band[i][j] == 255.0) {
                    for (Shape shape: accumulator.keySet()) {
                        if (shape.belongs(i, j)) {
                            accumulator.put(shape, accumulator.get(shape) + 1);
                        }
                    }
                }
            }
        }

        return accumulator
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > threshold)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

    }
}
