package ar.edu.itba.controllers.operations.mask;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.GaussianMask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by Nicolas Castano on 8/30/17.
 */
public class GaussController extends OperationController {
    public TextField size;
    public TextField sigma;

    @Inject
    public GaussController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        double s = Double.parseDouble(sigma.getText());
        int maskSize = Integer.parseInt(size.getText());

        this.eventBus.post(new GaussianMask(maskSize,s));
    }
}
