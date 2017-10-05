package ar.edu.itba.views.punctualOperations;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.VBox;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by root on 9/20/17.
 */
public class OtsuThresholdingView extends VBox {
    public OtsuThresholdingView(){
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.OTSU, this);
    }
}
