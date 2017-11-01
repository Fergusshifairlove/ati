package ar.edu.itba.models;

import ar.edu.itba.constants.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Luis on 29/10/2017.
 */
public class Position {
    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Iterable<Position> getNeighbours(Direction[] directions) {
        Set<Position> neighbours = new HashSet<>();
        for (int i = 0; i < directions.length; i++) {
            neighbours.add(new Position(x + directions[i].getXStep(), y + directions[i].getYStep()));
            neighbours.add(new Position(x - directions[i].getXStep(), y - directions[i].getYStep()));
        }
        return neighbours;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
