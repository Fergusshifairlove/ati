package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.controllers.operations.punctualOperations.ThresholdController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.Canny;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Created by Luis on 1/10/2017.
 */
public class CannyController extends ThresholdController{

    @Inject
    public CannyController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        double[] sigmas = {1.0};
        Canny canny = new Canny(sigmas, (x,y)->Math.sqrt(Math.pow(x,2) + Math.pow(y,2)), (x,y)->x.equals(y)?x:0.0);
        this.eventBus.post(canny);
    }
}
