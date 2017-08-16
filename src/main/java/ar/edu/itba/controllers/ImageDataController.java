package ar.edu.itba.controllers;

import ar.edu.itba.events.PixelSelected;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;


public class ImageDataController {
    public TextField selectedPixel;

    private EventBus eventBus;

    @Inject
    public ImageDataController(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void setSelectedPixel(PixelSelected pixel) {
        selectedPixel.setText(pixel.getColor().toString());
    }

    public void textChanged(ActionEvent event) {
        System.out.println(event);

    }
}
