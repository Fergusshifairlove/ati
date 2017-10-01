package ar.edu.itba.controllers;

import ar.edu.itba.events.*;
import ar.edu.itba.services.ImageService;
import ar.edu.itba.views.GreyPixelView;
import ar.edu.itba.views.borderOperations.*;
import ar.edu.itba.views.maskOperations.*;
import ar.edu.itba.views.noiseOperations.ExponentialNoiseView;
import ar.edu.itba.views.noiseOperations.GaussianNoiseView;
import ar.edu.itba.views.noiseOperations.RayleighNoiseView;
import ar.edu.itba.views.noiseOperations.SaltAndPepperNoiseView;
import ar.edu.itba.views.punctualOperations.ContrastView;
import ar.edu.itba.views.punctualOperations.GammaView;
import ar.edu.itba.views.punctualOperations.NegativeView;
import ar.edu.itba.views.punctualOperations.ThresholdView;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

import java.io.*;

public class MenuController {
    public MenuBar menuBar;
    private FileChooser fileChooser;
    private EventBus eventBus;
    private ImageService imageService;

    @Inject
    public MenuController(final EventBus eventBus, final ImageService imageService) {
        this.eventBus = eventBus;
        this.imageService = imageService;
    }

    @FXML
    public void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("RAW", "*.raw"),
                new FileChooser.ExtensionFilter("PGM", "*.pgm"),
                new FileChooser.ExtensionFilter("PPM", "*.ppm"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );
    }

    public void openFile(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null)
            eventBus.post(new OpenImage(file));

    }

    public void setPixel(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new GreyPixelView()));
    }

    public void saveFile(ActionEvent actionEvent) throws IOException{
        File file = fileChooser.showSaveDialog(menuBar.getScene().getWindow());
        if (file != null)
            this.eventBus.post(new SaveImage(file));
    }

    public void getNegative(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new NegativeView()));
    }

    public void getThreshold(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new ThresholdView()));
    }

    public void getGamma(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new GammaView()));
    }

    public void gaussianNoise(ActionEvent actionEvent) { eventBus.post(new NewOperation<>(new GaussianNoiseView()));}

    public void exponentialNoise(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new ExponentialNoiseView()));
    }

    public void rayleighNoise(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new RayleighNoiseView()));
    }

    public void meanMask(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new MeanView()));}
    public void medianMask(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new MedianView()));}
    public void weightedMedianMask(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new WeightedMedianView()));}
    public void gaussMask(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new GaussView()));}
    public void highPassMask(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new HighPassView()));}
    public void saltAndPepperNoise(ActionEvent actionEvent) { eventBus.post(new NewOperation<>(new SaltAndPepperNoiseView()));}

    public void prewitt(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new PrewittView()));}
    public void directionalprewitt(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new DirectionalPrewittView()));}
    public void sobel(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new SobelView()));}
    public void directionalsobel(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new DirectionalSobelView()));}
    public void zerolaplacian(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new ZeroLaplacianView()));}
    public void laplacian(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new LaplacianView()));}
    public void log(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new LoGView()));}

    public void equalize(ActionEvent event) {
        eventBus.post(new EqualizeImage());
    }

    public void getContrast(ActionEvent event) {
        eventBus.post(new NewOperation<>(new ContrastView()));
    }
}
