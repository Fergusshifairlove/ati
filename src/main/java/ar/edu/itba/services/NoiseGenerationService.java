package ar.edu.itba.services;

import ar.edu.itba.models.ImageMatrix;

public interface NoiseGenerationService {
    ImageMatrix getGausianNoiseMatrix(int width, int height, int image_type, double sigma, double mu);
    ImageMatrix getRayleighNoiseMatrix(int width, int height, int image_type, double xi);
    ImageMatrix getExponentialNoiseMatrix(int width, int height, double lambda);
}
