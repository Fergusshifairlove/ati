package ar.edu.itba.controllers.operations;

import ar.edu.itba.events.RemoveOperation;
import ar.edu.itba.events.RemoveOperations;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
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

    @Subscribe
    public void unsubscribe(RemoveOperations removeOperations) {
        System.out.println("removing controller");
        this.eventBus.unregister(this);
    }
}
