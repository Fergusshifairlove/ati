package ar.edu.itba.models.randomGenerators;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class RayleighRandomNumberGenerator implements RandomNumberGenerator{
    private ThreadLocalRandom random;
    private double xi;

    public RayleighRandomNumberGenerator(double xi) {
        this.random = ThreadLocalRandom.current();
        this.xi = xi;
    }

    @Override
    public double nextRandom() {
        return xi * Math.sqrt(-2.0* Math.log(1.0-random.nextDouble()));
    }

    @Override
    public DoubleStream doubles() {
        return DoubleStream.generate(this);
    }

    @Override
    public DoubleStream doubles(long size) {
        return DoubleStream.generate(this).limit(size);
    }

    @Override
    public double getAsDouble() {
        return nextRandom();
    }
}
