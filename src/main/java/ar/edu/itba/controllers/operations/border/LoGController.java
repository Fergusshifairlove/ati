package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.LoGMask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;

/**
 * Created by root on 9/20/17.
 */
public class LoGController extends OperationController{
    @Inject
    public LoGController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {

    }
    @Subscribe
    void apply(ApplyOperations applyOperations) {
        this.eventBus.post(new LoGMask(3,2.0));
    }
}
