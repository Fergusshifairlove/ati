package ar.edu.itba.constants;

public enum SusanType {
    BORDER(0.5), CORNER(0.75), NONE(0.0);

    private double match;

    SusanType(double match) {
        this.match = match;
    }

    public double getMatch() {
        return match;
    }
}
