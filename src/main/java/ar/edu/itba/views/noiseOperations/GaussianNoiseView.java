package ar.edu.itba.views.noiseOperations;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import static ar.edu.itba.App.INJECTOR;

public class GaussianNoiseView extends VBox{
    public GaussianNoiseView() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.GAUSSIAN, this);
    }

}
