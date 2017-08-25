package ar.edu.itba.models.randomGenerators;

import java.util.function.DoubleSupplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public interface RandomNumberGenerator extends DoubleSupplier{
    double nextRandom();
    DoubleStream doubles();
    DoubleStream doubles(long size);

}
