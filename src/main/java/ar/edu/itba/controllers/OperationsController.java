package ar.edu.itba.controllers;

import ar.edu.itba.events.NewOperation;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.OperationsConfirmed;
import ar.edu.itba.events.RemoveOperation;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Separator;
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

        ObservableList<Node> ops = this.operations.getChildren();
        if (ops.size() != 0)
            ops.add(new Separator());
        ops.add(operation.getView());
    }

    public void applyOperations(ActionEvent event) {
        this.eventBus.post(new ApplyOperations());
    }

    public void clearOperations(ActionEvent event) { this.operations.getChildren().clear();}

    public void confirmOperations(ActionEvent event) {
        clearOperations(null);
        this.eventBus.post(new OperationsConfirmed());}

    @Subscribe
    public void removeOperation(RemoveOperation removeOperation) {
        this.operations.getChildren().removeAll(removeOperation.getOperation());
    }
}
