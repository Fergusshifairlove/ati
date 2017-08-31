package ar.edu.itba.views.noiseOperations;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.VBox;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by Luis on 30/8/2017.
 */
public class SaltAndPepperNoiseView extends VBox {
    public SaltAndPepperNoiseView() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.SALT_AND_PEPPER, this);
    }
}
