package ar.edu.itba.views;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.GridPane;

import static ar.edu.itba.App.INJECTOR;

public class ImageDataView extends GridPane{
    public ImageDataView() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);

        fxmlLoaderService.load(FxmlEnum.DATA,this);
    }
}
