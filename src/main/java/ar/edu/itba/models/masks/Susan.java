package ar.edu.itba.models.masks;

import ar.edu.itba.constants.SusanType;

public class Susan extends Mask{
    private double threshold, precision;
    private SusanType type;

    public Susan(double threshold, double precision, SusanType type) {
        super(7);

        this.threshold = threshold;
        this.precision = precision;
        this.type = type;

        double[] row3 = {0, 0, 1, 1, 1, 0, 0};
        double[] row5 = {0, 1, 1, 1, 1, 1, 0};
        double[] row7 = {1, 1, 1, 1, 1, 1, 1};
        double[][] mask = {row3, row5, row7, row7, row7, row5, row3};
        this.mask = mask;
        this.filter = this::filter;
    }

    private Double filter(double[] values) {
        double accum = 0.0,val;
        for(int i =0; i<size; i++) {
            for (int j = 0; j < size; j++) {
                val = get(i, j) * values[i*size + j];
                if (Math.abs(val - values[3*size + 3]) < this.threshold) {
                    accum++;
                }
            }
        }
        accum = 1 - accum/37;
        if (Math.abs(accum - this.type.getMatch()) < this.precision) {
            return 255.0;
        }
        return 0.0;
    }
}
