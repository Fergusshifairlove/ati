package ar.edu.itba.models.random;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.models.random.generators.RandomNumberGenerator;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

/**
 * Created by Luis on 7/10/2017.
 */
public class RandomUtils {
    public static double[][] getNoiseBand(int width, int height, RandomNumberGenerator generator, NoiseType noiseType, double percentage) {
        long cant = Math.round(width * height * percentage);
        DoubleStream randoms = generator.doubles(cant);
        Iterable<Point> toModify = getPixelsToModify(width, height, cant);
        return getRandomBand(width, height, noiseType, toModify, randoms.iterator());
    }

    private static double[][] getRandomBand(int width, int height, NoiseType noiseType, Iterable<Point> toModify, Iterator<Double> generator) {
        double[][] noise = new double[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                noise[i][j] = noiseType.getNeutral();
            }
        }

        for (Point p : toModify) {
            noise[p.x][p.y] = generator.next();
        }

        return noise;
    }

    private static Iterable<Point> getPixelsToModify(int width, int height, long cant) {
        Set<Point> modified = new HashSet<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        Iterator<Integer> cols = random.ints(0, height).iterator();
        Iterator<Integer> rows = random.ints(0, width).iterator();

        do {
            modified.add(new Point(rows.next(), cols.next()));
        } while (modified.size() < cant);

        return modified;
    }
}
