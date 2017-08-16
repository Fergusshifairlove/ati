package ar.edu.itba;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.guice.GuiceModule;
import ar.edu.itba.service.FxmlLoaderService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    @FXML
    private MenuBox menuBox;


    public static Injector INJECTOR = Guice.createInjector(new GuiceModule());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        final FxmlLoaderService fxmlLoaderService = INJECTOR.getInstance(FxmlLoaderService.class);

        final Parent root = fxmlLoaderService.load(FxmlEnum.MAIN, null);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }
}
