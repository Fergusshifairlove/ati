package ar.edu.itba.controllers;

import ar.edu.itba.events.*;
import ar.edu.itba.models.GreyPixel;
import ar.edu.itba.models.RGBPixel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import static java.awt.Color.red;

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

    public void setGrey(ActionEvent event) {
        int g = Integer.parseInt(grey.getText());

        this.pixel = new GreyPixel(pixel.getX(), pixel.getY(), g);
        System.out.println("(" + g + ")");
        eventBus.post(new GreyPixelModified(pixel));
    }

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
