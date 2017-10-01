package ar.edu.itba.views.borderOperations;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.VBox;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by root on 9/20/17.
 */
public class DirectionalSobelView extends VBox {
    public DirectionalSobelView(){
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.DIRECTIONALSOBEL, this);
    }
}
