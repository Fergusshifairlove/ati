package ar.edu.itba.services.impl;

import ar.edu.itba.constants.FxmlEnum;
import ar.edu.itba.services.FxmlLoaderService;
import com.google.common.eventbus.EventBus;
import com.google.common.io.Resources;
import com.google.inject.Inject;
import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;

import java.io.IOException;

public final class FxmlLoaderServiceImpl implements FxmlLoaderService {
    private final Injector injector;
    private final EventBus eventBus;

    @Inject
    public FxmlLoaderServiceImpl(final Injector injector, final EventBus eventBus) {
        this.injector = injector;
        this.eventBus = eventBus;
    }

    /**
     * When creating the controller, use Guice to inject dependencies into it first and then register it to the event bus
     * before returning it.
     *
     * @param fxmlEnum FXML Enum
     * @return Node
     */

    public Parent load(final FxmlEnum fxmlEnum, Object root) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Resources.getResource(fxmlEnum.getPath()));
            loader.setBuilderFactory(new JavaFXBuilderFactory());
            loader.setControllerFactory(clazz -> {
                final Object controller = injector.getInstance(clazz);
                eventBus.register(controller);
                return controller;
            });
            if (root != null)
                loader.setRoot(root);
            return loader.load();
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load FXML from path: " + fxmlEnum.getPath(), e);
        }
    }

}
