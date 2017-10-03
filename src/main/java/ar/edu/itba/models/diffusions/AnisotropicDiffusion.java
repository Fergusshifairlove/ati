package ar.edu.itba.models.diffusions;

import ar.edu.itba.models.diffusions.detectors.Detector;

/**
 * Created by Luis on 2/10/2017.
 */
public class AnisotropicDiffusion extends Diffusion{
    public AnisotropicDiffusion(double lambda, Detector detector) {
        super(lambda, detector::g);
    }
}
