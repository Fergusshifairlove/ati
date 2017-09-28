package ar.edu.itba.views.histograms;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.VBox;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by Nicolas Castano on 8/25/17.
 */
public class RGBHistogramView extends VBox{
    public RGBHistogramView() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.RGBHISTOGRAM,this);
    }
}
