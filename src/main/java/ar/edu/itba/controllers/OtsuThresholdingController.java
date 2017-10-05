package ar.edu.itba.controllers;

import ar.edu.itba.controllers.operations.punctualOperations.ThresholdController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.thresholding.GlobalThresholding;
import ar.edu.itba.models.thresholding.OtsuThresholding;
import ar.edu.itba.models.thresholding.ThresholdFinder;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Created by Luis on 1/10/2017.
 */
public class OtsuThresholdingController extends ThresholdController{

    @Inject
    public OtsuThresholdingController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        ThresholdFinder th = new OtsuThresholding();
        this.eventBus.post(th);
    }
}
