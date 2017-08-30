package ar.edu.itba.controllers.operations.mask;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.WeightedMedianMask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;

/**
 * Created by root on 8/30/17.
 */
public class WeightedMedianController extends OperationController {

    @Inject
    public WeightedMedianController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        this.eventBus.post(new WeightedMedianMask(3));
    }
}
