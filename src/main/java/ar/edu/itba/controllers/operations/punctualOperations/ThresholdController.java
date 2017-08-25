package ar.edu.itba.controllers.operations.punctualOperations;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyPunctualOperation;
import ar.edu.itba.events.ApplyOperations;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.Slider;

/**
 * Created by Luis on 20/8/2017.
 */
public class ThresholdController extends OperationController {
    public Slider slider;

    @Inject
    public ThresholdController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        double threshold = slider.getValue();
        this.eventBus.post(new ApplyPunctualOperation(pixel -> pixel>threshold?255:0));
    }

}
