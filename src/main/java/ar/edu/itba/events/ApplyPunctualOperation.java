package ar.edu.itba.events;

import java.util.function.ToDoubleFunction;

/**
 * Created by Luis on 20/8/2017.
 */
public class ApplyPunctualOperation {
    ToDoubleFunction<Double> operator;

    public ApplyPunctualOperation(ToDoubleFunction<Double> operator) {
        this.operator = operator;
    }

    public ToDoubleFunction<Double> getOperator() {
        return operator;
    }
}
