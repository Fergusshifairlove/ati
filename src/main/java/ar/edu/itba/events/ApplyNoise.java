package ar.edu.itba.events;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.models.randomGenerators.RandomNumberGenerator;

public class ApplyNoise {
    private RandomNumberGenerator generator;
    private double percentage;
    private NoiseType noiseType;

    public ApplyNoise(RandomNumberGenerator generator, double percentage, NoiseType noiseType) {
        this.generator = generator;
        this.percentage = percentage;
        this.noiseType = noiseType;
    }

    public RandomNumberGenerator getGenerator() {
        return generator;
    }

    public double getPercentage() {
        return percentage;
    }

    public NoiseType getNoiseType() {
        return noiseType;
    }
}
