package ar.edu.itba.controllers.punctualOperations;

import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.ApplyPunctualOperation;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.TextField;

public class GammaController extends PunctualOperationController{
    public TextField gammaValue;

    @Inject
    public GammaController(EventBus eventBus) {
       super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        double gamma = Double.parseDouble(gammaValue.getText());
        double c = Math.pow(255, 1-gamma);
        this.eventBus.post(new ApplyPunctualOperation(pixel -> c * Math.pow(pixel, gamma)));
    }
}
