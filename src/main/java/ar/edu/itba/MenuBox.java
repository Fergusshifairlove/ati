package ar.edu.itba;

import ar.edu.itba.guice.GuiceModule;
import ar.edu.itba.service.FxmlLoaderService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuBox extends VBox{
    public MenuBox() throws IOException{
        final Injector injector = Guice.createInjector(new GuiceModule());
        final FxmlLoaderService fxmlLoaderService = injector.getInstance(FxmlLoaderService.class);

        fxmlLoaderService.load(FxmlEnum.MENU, this);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/menu.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.load();
    }
}
