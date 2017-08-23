package ar.edu.itba.controllers.punctualOperations;

import ar.edu.itba.events.ApplyPunctualOperation;
import ar.edu.itba.events.ApplyOperations;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.CheckBox;

/**
 * Created by Luis on 20/8/2017.
 */
public class NegativeController extends PunctualOperationController{
    public CheckBox check;
    private EventBus eventBus;

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
