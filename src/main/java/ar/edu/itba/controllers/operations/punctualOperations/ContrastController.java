package ar.edu.itba.controllers.operations.punctualOperations;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.ApplyPunctualOperation;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.TextField;

/**
 * Created by Luis on 30/8/2017.
 */
public class ContrastController extends OperationController{
    public TextField increase;
    public TextField r1;
    public TextField r2;

    @Inject
    public ContrastController(EventBus eventBus) {
        super(eventBus);
    }

    @Subscribe
    public void apply(ApplyOperations applyOperations) {
        int lowerLimit = Integer.parseInt(r1.getText());
        int upperLimit = Integer.parseInt(r2.getText());
        double i = Double.parseDouble(increase.getText());

        eventBus.post(new ApplyPunctualOperation(pixel -> increaseContrast(upperLimit, lowerLimit, i, pixel)));
    }

    private static double increaseContrast(int upper, int lower, double inc, double pixel) {
        if (pixel > upper)
            return pixel + pixel*inc;
        else if (pixel < lower)
            return pixel - (255-pixel)*inc;
        return pixel;
    }
}
