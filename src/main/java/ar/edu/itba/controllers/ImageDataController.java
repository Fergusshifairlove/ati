package ar.edu.itba.controllers;

import ar.edu.itba.events.ImageLoaded;
import ar.edu.itba.events.PixelSelected;
import ar.edu.itba.events.RGBPixelModified;
import ar.edu.itba.events.RGBPixelSelected;
import ar.edu.itba.models.Pixel;
import ar.edu.itba.models.RGBPixel;
import ar.edu.itba.views.RGBPixelView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class ImageDataController {
    public TextField x, y;
    public VBox data;

    private Pixel pixel;
    private EventBus eventBus;


    @Inject
    public ImageDataController(final EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @FXML
    public void initialize() {
//        red.setAlignment(Pos.BASELINE_RIGHT);
//        green.setAlignment(Pos.BASELINE_RIGHT);
//        blue.setAlignment(Pos.BASELINE_RIGHT);
        x.setAlignment(Pos.BASELINE_RIGHT);
        y.setAlignment(Pos.BASELINE_RIGHT);
    }

    @Subscribe
    public void setSelectedPixel(PixelSelected selected) {
        this.pixel = selected.getPixel();
//        red.setText(String.valueOf(pixel.getRed()));
//        green.setText(String.valueOf(pixel.getGreen()));
//        blue.setText(String.valueOf(pixel.getBlue()));
        //selectedPixel.setText(this.pixel.toString());
        x.setText(String.valueOf(pixel.getX()));
        y.setText(String.valueOf(pixel.getY()));
    }

    @Subscribe
    public void clearData(ImageLoaded imageLoaded) {
        if (imageLoaded.isRgb()) {
            ObservableList<Node> children = this.data.getChildren();
            children.remove(this.data.lookup(".grey"));
            RGBPixelView pixelView = new RGBPixelView();
            children.add(pixelView);
        }
        this.x.clear();
        this.y.clear();
    }

}
