package ar.edu.itba.controllers;

import ar.edu.itba.events.NewOperation;
import ar.edu.itba.events.OperationsConfirmed;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Created by Luis on 20/8/2017.
 */
public class OperationsController {
    public VBox operations;
    private EventBus eventBus;

    @Inject
    public OperationsController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void addNewOperation(NewOperation<Node> operation) {
        this.operations.getChildren().add(operation.getView());
    }

    public void applyOperations(ActionEvent event) {
        this.eventBus.post(new OperationsConfirmed());
    }
}
