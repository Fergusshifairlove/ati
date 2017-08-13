package ar.edu.itba;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.service.FxmlLoaderService;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static ar.edu.itba.App.INJECTOR;

public class EditorBox extends SplitPane {
    public EditorBox() throws IOException{
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);

        fxmlLoaderService.load(FxmlEnum.EDITOR,this);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/editor.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.load();
    }
}
