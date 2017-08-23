package ar.edu.itba.events;

import javafx.scene.Node;

public class RemoveOperation {
    private Node operation;

    public RemoveOperation(Node object) {
        this.operation = object;
    }

    public Node getOperation() {
        return operation;
    }
}
