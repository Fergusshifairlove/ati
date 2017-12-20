package ar.edu.itba.controllers.operations.cows;


import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.events.CountCows;
import ar.edu.itba.events.CowCount;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CowCounterController extends OperationController {
    public TextField maskSize;
    public TextField maxClusterDistance;
    public TextField minClusterSize;
    public Label count;
    public TextField maxSpread;

    @Inject
    public CowCounterController(EventBus eventBus) {
        super(eventBus);
    }


    @Subscribe
    void apply(ApplyOperations applyOperations) {
        int mask = Integer.parseInt(maskSize.getText());
        int distance = Integer.parseInt(maxClusterDistance.getText());
        int size = Integer.parseInt(minClusterSize.getText());
        double spread = Double.parseDouble(maxSpread.getText());
        this.eventBus.post(new CountCows(mask, distance, size, spread));
    }


    @Subscribe
    void getCount(CowCount count) {
        this.count.setText(count.getCount() + "");
    }
}
