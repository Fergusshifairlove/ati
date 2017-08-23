package ar.edu.itba.controllers.punctualOperations;

import ar.edu.itba.events.RemoveOperation;
import com.google.common.eventbus.EventBus;
import javafx.event.ActionEvent;
import javafx.scene.Node;


public abstract class PunctualOperationController {
    protected EventBus eventBus;

    public PunctualOperationController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void removeOperation(ActionEvent event) {
        this.eventBus.post(new RemoveOperation(((Node) event.getSource()).getParent()));
    }
}
