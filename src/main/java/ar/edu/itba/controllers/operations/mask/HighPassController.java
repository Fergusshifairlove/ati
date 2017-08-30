package ar.edu.itba.controllers.operations.mask;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.HighPassMask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;

/**
 * Created by root on 8/30/17.
 */
public class HighPassController extends OperationController{
    @Inject
    public HighPassController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        this.eventBus.post(new HighPassMask(3));
    }
}
