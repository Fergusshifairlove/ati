package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.Direction;
import ar.edu.itba.models.DirectionalMask;
import ar.edu.itba.models.SobelMask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/20/17.
 */
public class SobelController extends OperationController{
    @Inject
    public SobelController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {

    }

    @Subscribe
    void apply(ApplyOperations applyOperations) {
        List<Direction> directions=new ArrayList<Direction>();
        directions.add(Direction.HORIZONTAL);
        directions.add(Direction.VERTICAL);
        this.eventBus.post(new DirectionalMask(new SobelMask(),directions,null));
    }
}
