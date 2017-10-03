package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.masks.Direction;
import ar.edu.itba.models.masks.DirectionalMask;
import ar.edu.itba.models.masks.PrewittMask;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/20/17.
 */
public class PrewittController extends OperationController {
    @Inject
    public PrewittController(EventBus eventBus) {
        super(eventBus);
    }
    @FXML
    public void initialize() {

    }
    @Subscribe
    void apply(ApplyOperations applyOperations) {
        List<Direction> directions=new ArrayList<>();
        directions.add(Direction.HORIZONTAL);
        directions.add(Direction.VERTICAL);
        this.eventBus.post(new DirectionalMask(new PrewittMask(),directions,null));
    }
}
