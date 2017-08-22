package ar.edu.itba.events;

import javafx.scene.Node;

/**
 * Created by Luis on 20/8/2017.
 */
public class NewOperation<K extends Node> {
    private K view;

    public NewOperation(K view) {
        this.view = view;
    }

    public K getView() {
        return view;
    }
}
