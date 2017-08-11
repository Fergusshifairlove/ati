package ar.edu.itba;

import ar.edu.itba.guice.GuiceModule;
import ar.edu.itba.service.FxmlLoaderService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class EditorBox extends HBox {
    public EditorBox() throws IOException{
        final Injector injector = Guice.createInjector(new GuiceModule());
        final FxmlLoaderService fxmlLoaderService = injector.getInstance(FxmlLoaderService.class);

        fxmlLoaderService.load(FxmlEnum.EDITOR,this);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/editor.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.load();
    }
}
