package ar.edu.itba.models.shapes;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Luis on 20/10/2017.
 */
public class Line extends Shape{
    private int tita;
    private double ro;

    public Line(double delta, int tita, double ro) {
        super(delta);
        this.tita = tita;
        this.ro = ro;
    }

    @Override
    public boolean belongs(int x, int y) {
        return Math.abs(ro - x*Math.cos(Math.toRadians(tita)) - y*Math.sin(Math.toRadians(tita))) <= delta;
    }

    @Override
    public javafx.scene.shape.Shape toFxShape(Bounds bounds) {
        System.out.println(tita);
        System.out.println(ro);

        if (tita == 0) {
            javafx.scene.shape.Line line = new javafx.scene.shape.Line((int)ro, 0, (int)ro, bounds.getHeight());
            line.setStroke(Color.YELLOW);
            line.setStrokeWidth(2);
            return line;
        }
        if (tita == 90 || tita == -90   ) {
            javafx.scene.shape.Line line = new javafx.scene.shape.Line(0, (int) ro, bounds.getWidth(), (int)ro);
            line.setStroke(Color.YELLOW);
            line.setStrokeWidth(2);
            return line;
        }

        int first=-1, last=-1;
        for (int i = 0; i < bounds.getWidth(); i++) {
            if(bounds.contains(i,eval(i))) {
                if(first == -1)
                    first = i;
                last = i;
            }
        }
        javafx.scene.shape.Line line = new javafx.scene.shape.Line(first, eval(first), last, eval(last));
        line.setStroke(Color.YELLOW);
        line.setStrokeWidth(2);

        return line;
    }

    private int eval(int x) {
        if (tita == 0.0)
            return (int)ro;
        return (int) ((ro - x*Math.cos(Math.toRadians(tita))) / Math.sin(Math.toRadians(tita)));
    }

    private double inverse(int y) {
        if (tita == Math.PI/2)
            return ro;
        return (Math.sin(tita)*y - ro) / Math.cos(tita);
    }

}
