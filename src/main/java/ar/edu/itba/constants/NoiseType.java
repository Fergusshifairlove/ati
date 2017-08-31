package ar.edu.itba.constants;

import java.util.function.BinaryOperator;

public enum NoiseType {
    ADDITIVE(0, (p1, p2) -> p1 + p2), MULTIPLICATIVE(1, (p1, p2) -> p1 * p2), SALT_AND_PEPPER(-1, (p1, p2) -> p2 == -1? p1 : p2);

    private double neutral;
    private BinaryOperator<Double> operator;
    NoiseType(double neutral, BinaryOperator<Double> operator) {
        this.neutral = neutral;
        this.operator = operator;
    }

    public double getNeutral() {
        return neutral;
    }

    public BinaryOperator<Double> getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        switch (this) {
            case ADDITIVE:
                return "+";
            case MULTIPLICATIVE:
                return "*";
            default:
                return "";
        }
    }
}
