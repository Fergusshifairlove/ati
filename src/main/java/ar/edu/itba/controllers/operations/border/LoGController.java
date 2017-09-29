package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.Direction;
import ar.edu.itba.models.DirectionalMask;
import ar.edu.itba.models.LoGMask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/20/17.
 */
public class LoGController extends OperationController{
    @Inject
    public LoGController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {

    }
    @Subscribe
    void apply(ApplyOperations applyOperations) {
        List<Direction> directions;
        directions = new ArrayList<>();
        directions.add(Direction.HORIZONTAL);
        this.eventBus.post(new DirectionalMask(new LoGMask(3,2.0),directions) );
    }
}
