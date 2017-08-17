package ar.edu.itba;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.guice.GuiceModule;
import ar.edu.itba.services.FxmlLoaderService;
import ar.edu.itba.services.ImageService;
import com.google.common.eventbus.EventBus;
import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
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
        final EventBus eventBus = INJECTOR.getInstance(EventBus.class);
        final ImageService imageService = new ImageService();
        eventBus.register(imageService);

        final Parent root = fxmlLoaderService.load(FxmlEnum.MAIN, null);

        primaryStage.setTitle("FXML Welcome");
        primaryStage.setScene(new Scene(root));
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.show();
    }
}
