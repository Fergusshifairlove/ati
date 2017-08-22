package ar.edu.itba.controllers;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.PixelSelected;
import ar.edu.itba.events.RGBPixelModified;
import ar.edu.itba.events.RGBPixelSelected;
import ar.edu.itba.models.RGBPixel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

/**
 * Created by Luis on 18/8/2017.
 */
public class RGBPixelController {
    public TextField red, green, blue;
    private RGBPixel pixel;
    private EventBus eventBus;

    @Inject
    public RGBPixelController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void setRGB(ActionEvent event) {
        int r = Integer.parseInt(red.getText());
        int g = Integer.parseInt(green.getText());
        int b = Integer.parseInt(blue.getText());

        this.pixel = new RGBPixel(pixel.getX(), pixel.getY(), r, g, b);
        System.out.println("(" + r + ", " + g + ", " + b + ")");
        eventBus.post(new RGBPixelModified(pixel));
    }

    public void clearData(ImageLoaded loaded) {
        this.red.clear();
        this.green.clear();
        this.blue.clear();
        this.pixel =null;
    }

    @Subscribe
    public void setSelectedPixel(PixelSelected selected) {
        this.pixel = (RGBPixel) selected.getPixel();
        red.setText(String.valueOf(pixel.getRed()));
        green.setText(String.valueOf(pixel.getGreen()));
        blue.setText(String.valueOf(pixel.getBlue()));
    }
}
