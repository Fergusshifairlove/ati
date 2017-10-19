package ar.edu.itba.controllers;

import ar.edu.itba.events.*;
import ar.edu.itba.models.GreyPixel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

/**
 * Created by Luis on 18/8/2017.
 */
public class GreyPixelController {
    public TextField grey;
    private GreyPixel pixel;
    private EventBus eventBus;

    @Inject
    public GreyPixelController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void clearData(ImageLoaded loaded) {
        this.grey.clear();
        this.pixel = null;
    }

    @Subscribe
    public void setSelectedPixel(PixelSelected selected) {
        this.pixel = (GreyPixel) selected.getPixel();
        grey.setText(String.valueOf(pixel.getGrey()));
    }
}
