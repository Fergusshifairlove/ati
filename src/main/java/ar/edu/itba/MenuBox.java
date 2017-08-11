package ar.edu.itba;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MenuBox extends VBox{
    public MenuBox() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("menu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
    }
}
