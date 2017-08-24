package ar.edu.itba.services.impl;

import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.services.NoiseGenerationService;

public class NoiseGenerationServiceImpl implements NoiseGenerationService{
    @Override
    public ImageMatrix getGausianNoiseMatrix(int width, int height, int image_type, double sigma, double mu) {
         return null;
    }

    @Override
    public ImageMatrix getRayleighNoiseMatrix(int width, int height, int image_type, double xi) {
        return null;
    }

    @Override
    public ImageMatrix getExponentialNoiseMatrix(int width, int height, double lambda) {
        return null;
    }
}
