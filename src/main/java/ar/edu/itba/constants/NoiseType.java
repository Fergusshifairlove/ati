package ar.edu.itba.constants;

public enum NoiseType {
    ADDITIVE(0), MULTIPLICATIVE(1);

    private double neutral;

    NoiseType(double neutral) {
        this.neutral = neutral;
    }

    public double getNeutral() {
        return neutral;
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
