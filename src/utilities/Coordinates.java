package utilities;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinates {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinates(Coordinates coords) {
        this.x = coords.getX();
        this.y = coords.getY();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Coordinates incrementAndNew(int x, int y) {

        return new Coordinates(this.getX() + x, this.getY() + y);
    }

    public Coordinates() {
        this(0, 0);
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void increment(Coordinates coords) {
        this.x += coords.getX();
        this.y += coords.getY();
    }

    public void incrementX(int value) {
        this.x += value;
    }

    public void incrementY(int value) {
        this.y += value;
    }

    public void decrementY(int value) {
        this.y -= value;
    }

    public void decrementX(int value) {
        this.x -= value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return this.x == that.getX() && this.y == that.getY();
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public static boolean contains(ArrayList<Coordinates> coordList, Coordinates coords) {
        for (Coordinates coord : coordList) {
            if (coord.getX() == coords.getX() && coords.getY() == coord.getY()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
