package ar.edu.itba.controllers.operations.border;

import ar.edu.itba.controllers.operations.OperationController;
import ar.edu.itba.events.ApplyOperations;
import ar.edu.itba.models.masks.Direction;
import ar.edu.itba.models.masks.DirectionalMask;
import ar.edu.itba.models.masks.LoGMask;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 9/20/17.
 */
public class LoGController extends OperationController{
    public TextField umbral;
    public TextField sigma;
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
        double s = Double.parseDouble(sigma.getText());
        double s2 = Double.parseDouble(umbral.getText());
        List<Double> params = new ArrayList<>();
        params.add(s);
        params.add(s2);
        this.eventBus.post(new DirectionalMask(new LoGMask(3,s,s2),directions,params));
    }
}
