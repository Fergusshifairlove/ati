package ar.edu.itba.views;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.IOException;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by Luis on 20/8/2017.
 */
public class OperationsView extends BorderPane{
    public OperationsView() throws IOException {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.OPERATIONS,this);
    }
}
