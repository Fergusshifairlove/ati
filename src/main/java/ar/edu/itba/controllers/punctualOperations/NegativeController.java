package ar.edu.itba.controllers.punctualOperations;

import ar.edu.itba.events.ApplyPunctualOperation;
import ar.edu.itba.events.OperationsConfirmed;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

/**
 * Created by Luis on 20/8/2017.
 */
public class NegativeController {
    private EventBus eventBus;

    @Inject
    public NegativeController(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Subscribe
    public void apply(OperationsConfirmed operationsConfirmed) {
        eventBus.post(new ApplyPunctualOperation(pixel -> 255 - pixel));
    }
}
