package ar.edu.itba.controllers.operations.diffusion;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyNoise;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.DiffuseImage;
import ar.edu.itba.models.diffusions.IsotropicDiffusion;
import ar.edu.itba.models.randomGenerators.GaussianRandomNumberGenerator;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.TextField;

/**
 * Created by Luis on 3/10/2017.
 */
public class IsotropicController extends OperationController{
    public TextField lambda;
    public TextField times;

    @Inject
    public IsotropicController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        double l = Double.parseDouble(lambda.getText());
        int t = Integer.parseInt(times.getText());
        this.eventBus.post(new DiffuseImage(new IsotropicDiffusion(l), t));
    }
}
