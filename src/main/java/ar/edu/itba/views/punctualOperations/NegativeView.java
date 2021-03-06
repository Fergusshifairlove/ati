package ar.edu.itba.views.punctualOperations;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.HBox;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by Luis on 20/8/2017.
 */
public class NegativeView extends HBox{
    public NegativeView() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.NEGATIVE, this);
    }
}