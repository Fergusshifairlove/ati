package ar.edu.itba.controllers.operations.diffusion;

import ar.edu.itba.constants.NoiseType;
import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.DiffuseImage;
import ar.edu.itba.models.diffusions.AnisotropicDiffusion;
import ar.edu.itba.models.diffusions.IsotropicDiffusion;
import ar.edu.itba.models.diffusions.detectors.Detector;
import ar.edu.itba.models.diffusions.detectors.Leclerc;
import ar.edu.itba.models.diffusions.detectors.Lorentz;
import ar.edu.itba.models.diffusions.detectors.SigmaDetector;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.sun.javafx.collections.ObservableSequentialListWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Arrays;

/**
 * Created by Luis on 3/10/2017.
 */
public class AnisotropicController extends OperationController{
    public TextField lambda;
    public TextField times;
    public ComboBox<Detector> detector;
    public TextField sigma;

    @Inject
    public AnisotropicController(EventBus eventBus) {
        super(eventBus);
    }

    @FXML
    public void initialize() {
        Detector[] detectors = {new Leclerc(), new Lorentz()};
        detector.getItems().addAll(new ObservableSequentialListWrapper<>(Arrays.asList(detectors)));

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        double l = Double.parseDouble(lambda.getText());
        double s = Double.parseDouble(sigma.getText());
        int t = Integer.parseInt(times.getText());
        Detector d = detector.getValue();
        if(d == null)
            return;
        if (d instanceof SigmaDetector) {
            ((SigmaDetector)d).setSigma(s);
        }

        this.eventBus.post(new DiffuseImage(new AnisotropicDiffusion(l, d), t));
    }
}
