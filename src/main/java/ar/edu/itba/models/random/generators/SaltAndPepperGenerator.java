package ar.edu.itba.models.random.generators;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

/**
 * Created by Luis on 30/8/2017.
 */
public class SaltAndPepperGenerator implements RandomNumberGenerator{
    private ThreadLocalRandom random;
    private double p0, p1;

    public SaltAndPepperGenerator(double p0, double p1) {
        this.random = ThreadLocalRandom.current();
        this.p0 = p0;
        this.p1 = p1;
    }

    public SaltAndPepperGenerator() {
        this(0.8, 0.2);
    }

    @Override
    public double nextRandom() {
        double x = random.nextDouble();
        return x >= p0 ? 255 : x <= p1 ? 0 : -1;
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
