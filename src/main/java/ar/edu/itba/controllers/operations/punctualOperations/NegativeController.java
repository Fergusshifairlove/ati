package ar.edu.itba.controllers.operations.punctualOperations;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyPunctualOperation;
import ar.edu.itba.events.ApplyOperations;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.CheckBox;

/**
 * Created by Luis on 20/8/2017.
 */
public class NegativeController extends OperationController {
    public CheckBox check;

    @Inject
    public NegativeController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        if (check.isSelected())
            eventBus.post(new ApplyPunctualOperation(pixel -> 255 - pixel));
    }

}
