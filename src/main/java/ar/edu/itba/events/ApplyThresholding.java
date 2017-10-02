package ar.edu.itba.events;

import ar.edu.itba.models.thresholding.ThresholdFinder;

/**
 * Created by Luis on 1/10/2017.
 */
public class ApplyThresholding {
    private ThresholdFinder finder;

    public ApplyThresholding(ThresholdFinder finder) {
        this.finder = finder;
    }

    public ThresholdFinder getFinder() {
        return finder;
    }
}
