package ar.edu.itba.models.diffusions;

/**
 * Created by Luis on 2/10/2017.
 */
public class IsotropicDiffusion extends Diffusion{
    public IsotropicDiffusion(double lambda) {
        super(lambda, (x) -> 1);
    }
}
