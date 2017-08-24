package ar.edu.itba.views.punctualOperations;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.HBox;

import static ar.edu.itba.App.INJECTOR;

public class GammaView extends HBox{
    public GammaView() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.GAMMA, this);
    }
}
