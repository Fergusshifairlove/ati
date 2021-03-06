package ar.edu.itba.controllers;

import ar.edu.itba.events.*;
import ar.edu.itba.services.ImageService;
import ar.edu.itba.views.cows.CowCounterView;
import ar.edu.itba.views.features.HarrisView;
import ar.edu.itba.views.GreyPixelView;
import ar.edu.itba.views.borderOperations.LaplacianView;
import ar.edu.itba.views.borderOperations.LoGView;
import ar.edu.itba.views.borderOperations.PrewittView;
import ar.edu.itba.views.borderOperations.SobelView;
import ar.edu.itba.views.diffusion.AnisotropicView;
import ar.edu.itba.views.diffusion.IsotropicView;
import ar.edu.itba.views.borderOperations.*;
import ar.edu.itba.views.features.SiftView;
import ar.edu.itba.views.maskOperations.*;
import ar.edu.itba.views.noiseOperations.ExponentialNoiseView;
import ar.edu.itba.views.noiseOperations.GaussianNoiseView;
import ar.edu.itba.views.noiseOperations.RayleighNoiseView;
import ar.edu.itba.views.noiseOperations.SaltAndPepperNoiseView;
import ar.edu.itba.views.punctualOperations.*;
import com.google.common.eventbus.EventBus;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class MenuController {
    public MenuBar menuBar;
    private FileChooser fileChooser;
    private DirectoryChooser directoryChooser;
    private EventBus eventBus;
    private ImageService imageService;
    private Map<String,FileChooser.ExtensionFilter> filters;

    @Inject
    public MenuController(final EventBus eventBus, final ImageService imageService) {
        this.eventBus = eventBus;
        this.imageService = imageService;
        this.filters = new HashMap<>();
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
                new FileChooser.ExtensionFilter("MP4", "*.mp4"),
                new FileChooser.ExtensionFilter("All Images", "*.*")
        );

        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open video directory");
    }

    public void openFile(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null)
            if (file.getName().endsWith(".mp4")) {
                eventBus.post(new OpenVideo(file));
                return;
            }

            eventBus.post(new OpenImage(file));

    }

    public void openDirectory(ActionEvent actionEvent) throws IOException {
        directoryChooser.setTitle("Open Video Directory");
        File file = directoryChooser.showDialog(menuBar.getScene().getWindow());
        if (file != null)
            eventBus.post(new FindObjectInVideo(file));

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

    public void globalThreshold(ActionEvent event) {
        eventBus.post(new NewOperation<>(new GlobalThresholdView()));
    }
    public void otsuThreshold(ActionEvent event) {
        eventBus.post(new NewOperation<>(new OtsuThresholdingView()));
    }
    public void isotropic(ActionEvent event) {
        eventBus.post(new NewOperation<>(new IsotropicView()));
    }
    public void anisotropic(ActionEvent event) {
        eventBus.post(new NewOperation<>(new AnisotropicView()));
    }

    public void canny(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new CannyView()));}
    public void susan(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new SusanView()));}
    public void hough(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new HoughView()));}
    public void cut(ActionEvent actionEvent){eventBus.post(new CutImage());}
    public void findObject(ActionEvent actionEvent) {eventBus.post(new FindObjectInImage());}
    public void createWhiteSquare(ActionEvent actionEvent){eventBus.post(new CreateWhiteSquare());}
    public void harris(ActionEvent actionEvent){eventBus.post(new NewOperation<>(new HarrisView()));}
    public void siftFeatures(ActionEvent actionEvent){eventBus.post(new GetSIFTFeatures());}

    public void sift(ActionEvent actionEvent) {
        fileChooser.setTitle("Open compare image");
        File file = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
        if (file != null) {
            eventBus.post(new CompareImages(file));
            eventBus.post(new NewOperation<>(new SiftView()));
        }
    }

    public void onlySift(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new SiftView()));
    }

    public void countCows(ActionEvent actionEvent) {
        eventBus.post(new NewOperation<>(new CowCounterView()));
    }
}

