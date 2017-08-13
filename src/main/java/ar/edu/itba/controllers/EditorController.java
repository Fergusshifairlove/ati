package ar.edu.itba.controllers;

import ar.edu.itba.events.ImageLoaded;
import com.google.common.eventbus.Subscribe;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class EditorController {
    public HBox holder;
    public ImageView before;
    public ImageView after;

    @Subscribe
    public void showImage(ImageLoaded imageLoaded) {
        Image image = new Image(imageLoaded.getImg(),600,400,true,true);
        before.setImage(image);
    }
}
