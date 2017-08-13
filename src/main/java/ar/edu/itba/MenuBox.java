package ar.edu.itba;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.service.FxmlLoaderService;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static ar.edu.itba.App.INJECTOR;

public class MenuBox extends VBox{
    public MenuBox() throws IOException{
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);

        fxmlLoaderService.load(FxmlEnum.MENU, this);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/menu.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.load();
    }
}
