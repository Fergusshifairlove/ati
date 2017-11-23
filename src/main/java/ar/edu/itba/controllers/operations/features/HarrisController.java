package ar.edu.itba.controllers.operations.features;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.HarrisCorners;
import ar.edu.itba.models.Harris;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.Slider;

/**
 * Created by Luis on 20/8/2017.
 */
public class HarrisController extends OperationController {
    public Slider slider;

    @Inject
    public HarrisController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        double threshold = slider.getValue();
        this.eventBus.post(new HarrisCorners(new Harris(threshold)));
    }

}
