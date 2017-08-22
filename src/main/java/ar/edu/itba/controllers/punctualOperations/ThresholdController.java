package ar.edu.itba.controllers.punctualOperations;

import ar.edu.itba.events.ApplyPunctualOperation;
import ar.edu.itba.events.OperationsConfirmed;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.TextField;

/**
 * Created by Luis on 20/8/2017.
 */
public class ThresholdController {
    private EventBus eventBus;
    public TextField thresholdField;

    @Inject
    public ThresholdController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void apply(OperationsConfirmed operationsConfirmed) {
        double threshold = Double.parseDouble(thresholdField.getText());
        this.eventBus.post(new ApplyPunctualOperation(pixel -> pixel>threshold?255:0));
    }
}
