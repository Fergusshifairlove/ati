package ar.edu.itba.models.random.generators;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

public class GaussianRandomNumberGenerator implements RandomNumberGenerator {
    private ThreadLocalRandom random;
    double sigma, mu;

    public GaussianRandomNumberGenerator(double sigma, double mu) {
        this.random =  ThreadLocalRandom.current();
        this.sigma = sigma;
        this.mu = mu;
    }

    @Override
    public double nextRandom() {
        return this.random.nextGaussian()*sigma + mu;
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
        return this.nextRandom();
    }
}
