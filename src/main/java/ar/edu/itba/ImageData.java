package ar.edu.itba;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.service.FxmlLoaderService;
import javafx.scene.layout.GridPane;

import static ar.edu.itba.App.INJECTOR;

public class ImageData extends GridPane{
    public ImageData() {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);

        fxmlLoaderService.load(FxmlEnum.DATA,this);
    }
}
