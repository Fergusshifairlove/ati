package ar.edu.itba.models.randomGenerators;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class ExponentialRandomNumberGenerator implements RandomNumberGenerator{
    private ThreadLocalRandom random;
    private double lambda;

    public ExponentialRandomNumberGenerator(double lambda) {
        this.random = ThreadLocalRandom.current();
        this.lambda = lambda;
    }

    @Override
    public double nextRandom() {
        return Math.log(1-random.nextDouble())/(-lambda);
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
