package ar.edu.itba.events;

import ar.edu.itba.models.diffusions.Diffusion;

/**
 * Created by Luis on 3/10/2017.
 */
public class DiffuseImage {
    private Diffusion diffusion;
    private int times;

    public DiffuseImage(Diffusion diffusion, int times) {
        this.diffusion = diffusion;
        this.times = times;
    }

    public Diffusion getDiffusion() {
        return diffusion;
    }

    public int getTimes() {
        return times;
    }
}
