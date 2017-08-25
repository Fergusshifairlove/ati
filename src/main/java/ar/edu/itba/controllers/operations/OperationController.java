package ar.edu.itba.controllers.operations;

import ar.edu.itba.events.RemoveOperation;
import com.google.common.eventbus.EventBus;
import javafx.event.ActionEvent;
import javafx.scene.Node;


public abstract class OperationController {
    protected EventBus eventBus;

    public OperationController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void removeOperation(ActionEvent event) {
        this.eventBus.post(new RemoveOperation(((Node) event.getSource()).getParent()));
    }
}
