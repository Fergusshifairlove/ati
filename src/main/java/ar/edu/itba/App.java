package ar.edu.itba;

import com.google.common.eventbus.EventBus;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @FXML
    private MenuBox menuBox;

    EventBus eventBus = new EventBus();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(root, 300, 275);
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.show();
    }
}
