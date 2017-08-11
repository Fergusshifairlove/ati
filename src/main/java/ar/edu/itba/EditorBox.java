package ar.edu.itba;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class EditorBox extends HBox {
    public EditorBox() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("editor.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
    }
}
