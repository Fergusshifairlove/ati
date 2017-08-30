package ar.edu.itba.events;

import ar.edu.itba.models.Histogram;

/**
 * Created by Nicolas Castano on 8/25/17.
 */
public class LoadHistogram {
    private Histogram histogram;

    public LoadHistogram(Histogram histogram) {
        this.histogram = histogram;
    }

    public Histogram getHistogram() {
        return histogram;
    }
}
