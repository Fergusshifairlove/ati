package ar.edu.itba.models.random.generators;

import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;

public interface RandomNumberGenerator extends DoubleSupplier{
    double nextRandom();
    DoubleStream doubles();
    DoubleStream doubles(long size);

}
