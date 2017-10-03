package ar.edu.itba.views.diffusion;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static ar.edu.itba.App.INJECTOR;

/**
 * Created by Luis on 2/10/2017.
 */
public class AnisotropicView extends VBox{
    public AnisotropicView(){
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);
        fxmlLoaderService.load(FxmlEnum.ANISOTROPIC,this);
    }
}
