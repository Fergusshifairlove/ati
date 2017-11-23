package ar.edu.itba.controllers.operations.features;

import ar.edu.itba.constants.SIFTComparation;
import ar.edu.itba.controllers.operations.punctualOperations.ThresholdController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.CompareSift;
import ar.edu.itba.models.Canny;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.Label;

/**
 * Created by Luis on 1/10/2017.
 */
public class SiftController extends ThresholdController{

    public Label equals;

    @Inject
    public SiftController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        this.eventBus.post(new CompareSift());
    }

    @Subscribe
    public void response(SIFTComparation comparation) {
        this.equals.setText(comparation.toString());
    }
}
