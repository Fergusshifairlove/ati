package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.constants.Direction;
import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.constants.SusanType;
import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.masks.DirectionalMask;
import ar.edu.itba.models.masks.LoGMask;
import ar.edu.itba.models.masks.Susan;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.sun.javafx.collections.ObservableSequentialListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 9/20/17.
 */
public class SusanController extends OperationController{
    public TextField threshold;
    public TextField error;
    public ComboBox<SusanType> types;

    @Inject
    public SusanController(EventBus eventBus) {
        super(eventBus);
    }

    @FXML
    public void initialize() {
        SusanType[] susanTypes = {SusanType.BORDER, SusanType.CORNER, SusanType.NONE};
        types.getItems().addAll(new ObservableSequentialListWrapper<>(Arrays.asList(susanTypes)));

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        double t = Double.parseDouble(threshold.getText());
        double precision = Double.parseDouble(error.getText());
        SusanType type = types.getValue();
        Susan susan = new Susan(t,precision,type);
        this.eventBus.post(susan);
    }
}
