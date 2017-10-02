package ar.edu.itba.views.punctualOperations;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import ar.edu.itba.views.OperationsView;
import javafx.scene.layout.VBox;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by Luis on 1/10/2017.
 */
public class GlobalThresholdView extends VBox {
    public GlobalThresholdView() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.GLOBAL_THRESHOLD, this);
    }
}
