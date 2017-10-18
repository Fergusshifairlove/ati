package ar.edu.itba.constants;

/**
 * Created by root on 9/16/17.
 */
public enum Direction {
    HORIZONTAL(1,0),
    VERTICAL(0,1),
    DIAGONAL_LEFT(1,-11),
    DIAGONAL_RIGHT(1,1);

    Direction(int xStep, int yStep) {
        this.xStep = xStep;
        this.yStep = yStep;
    }

    private int xStep,yStep;

    public int getXStep() {
        return xStep;
    }

    public int getYStep() {
        return yStep;
    }
}
