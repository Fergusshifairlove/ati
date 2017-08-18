package ar.edu.itba.views;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import javafx.scene.control.SplitPane;

import java.io.IOException;

import static ar.edu.itba.App.INJECTOR;

public class EditorView extends SplitPane {
    public EditorView() throws IOException{
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);

        fxmlLoaderService.load(FxmlEnum.EDITOR,this);
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/editor.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.load();
    }
}
