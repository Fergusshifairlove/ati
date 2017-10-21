package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.Canny;
import ar.edu.itba.models.Hough;
import ar.edu.itba.models.shapes.generators.CircleGenerator;
import ar.edu.itba.models.shapes.generators.LineGenerator;
import ar.edu.itba.models.shapes.generators.ShapeGenerator;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.sun.javafx.collections.ObservableSequentialListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;

/**
 * Created by root on 9/20/17.
 */
public class HoughController extends OperationController{
    public TextField threshold;
    public TextField error;
    public ComboBox<ShapeGenerator> types;

    @Inject
    public HoughController(EventBus eventBus) {
        super(eventBus);
    }

    @FXML
    public void initialize() {
        ShapeGenerator[] susanTypes = {new LineGenerator(), new CircleGenerator()};
        types.getItems().addAll(new ObservableSequentialListWrapper<>(Arrays.asList(susanTypes)));

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        int t = Integer.parseInt(threshold.getText());
        double precision = Double.parseDouble(error.getText());
        ShapeGenerator generator = types.getValue();
        this.eventBus.post(new Hough(generator, t, precision));
    }
}
