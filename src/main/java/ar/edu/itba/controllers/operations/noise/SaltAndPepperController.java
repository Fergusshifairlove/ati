package ar.edu.itba.controllers.operations.noise;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyNoise;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.randomGenerators.SaltAndPepperGenerator;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.TextField;

/**
 * Created by Luis on 30/8/2017.
 */
public class SaltAndPepperController extends OperationController{
    public TextField white;
    public TextField black;
    public TextField percentage;

    @Inject
    public SaltAndPepperController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        double w = Double.parseDouble(white.getText());
        double b = Double.parseDouble(black.getText());
        double p = Double.parseDouble(percentage.getText());

        eventBus.post(new ApplyNoise(new SaltAndPepperGenerator(1-w, b), p, NoiseType.SALT_AND_PEPPER));
    }
}
