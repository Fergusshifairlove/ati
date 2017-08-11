package ar.edu.itba.controllers;

import ar.edu.itba.ImageLoaded;
import com.google.common.eventbus.Subscribe;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class EditorController {
    public HBox holder;
    public Label test;

    @Subscribe
    public void showImage(ImageLoaded imageLoaded) {
        test.setText("si");
    }
}
