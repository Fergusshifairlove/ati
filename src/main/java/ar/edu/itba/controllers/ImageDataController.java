package ar.edu.itba.controllers;

import ar.edu.itba.events.*;
import ar.edu.itba.models.ImageMatrix;
import ar.edu.itba.models.Pixel;
import ar.edu.itba.services.ImageService;
import ar.edu.itba.views.GreyPixelView;
import ar.edu.itba.views.RGBPixelView;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.awt.image.BufferedImage;


public class ImageDataController {
    public TextField x, y;
    public VBox data;

    private Pixel pixel;
    private EventBus eventBus;
    private ImageService imageService;
    private int currentImgType;


    @Inject
    public ImageDataController(final EventBus eventBus, final ImageService imageService) {
        this.eventBus = eventBus;
        this.imageService = imageService;
    }

    @FXML
    public void initialize() {
        x.setAlignment(Pos.BASELINE_RIGHT);
        y.setAlignment(Pos.BASELINE_RIGHT);
    }

    @Subscribe
    public void setSelectedPixel(PixelSelected selected) {
        this.pixel = selected.getPixel();
        x.setText(String.valueOf(pixel.getX()));
        y.setText(String.valueOf(pixel.getY()));
    }

    @Subscribe
    public void clearData(ImageLoaded imageLoaded) {
        ObservableList<Node> children = this.data.getChildren();
        this.x.clear();
        this.y.clear();
        int type = imageLoaded.getImage().getType();

        if (this.currentImgType == type)
            return;
        this.currentImgType = type;

        if (type == BufferedImage.TYPE_INT_RGB || type == BufferedImage.TYPE_3BYTE_BGR) {
            children.remove(this.data.lookup("#grey"));
            RGBPixelView pixelView = new RGBPixelView();
            pixelView.setId("rgb");
            children.add(pixelView);
        } else {
            children.remove(this.data.lookup("#rgb"));
            GreyPixelView pixelView = new GreyPixelView();
            pixelView.setId("grey");
            children.add(pixelView);
        }
    }

}
