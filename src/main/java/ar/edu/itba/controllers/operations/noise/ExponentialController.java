package ar.edu.itba.controllers.operations.noise;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyNoise;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.random.generators.ExponentialRandomNumberGenerator;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.sun.javafx.collections.ObservableSequentialListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;

public class ExponentialController extends OperationController{
    public TextField lamda;
    public TextField percentage;
    public ComboBox<NoiseType> type;

    @Inject
    public ExponentialController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {
        NoiseType[] noiseTypes = {NoiseType.ADDITIVE, NoiseType.MULTIPLICATIVE};
        type.getItems().addAll(new ObservableSequentialListWrapper<>(Arrays.asList(noiseTypes)));

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        double ptg = Double.parseDouble(percentage.getText());
        double l = Double.parseDouble(lamda.getText());
        NoiseType t = type.getValue();
        if (t == null)
            return;
        this.eventBus.post(new ApplyNoise(new ExponentialRandomNumberGenerator(l), ptg, t));
    }

}
