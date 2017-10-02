package ar.edu.itba.controllers;

import ar.edu.itba.controllers.operations.punctualOperations.ThresholdController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.ApplyPunctualOperation;
import ar.edu.itba.events.ApplyThresholding;
import ar.edu.itba.models.thresholding.GlobalThresholding;
import ar.edu.itba.models.thresholding.ThresholdFinder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Created by Luis on 1/10/2017.
 */
public class GlobalThresholdController extends ThresholdController{

    @Inject
    public GlobalThresholdController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        double threshold = slider.getValue();
        ThresholdFinder th = new GlobalThresholding(threshold,0.001);

        this.eventBus.post(th);
    }
}
