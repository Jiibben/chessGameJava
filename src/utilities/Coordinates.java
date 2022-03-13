package utilities;

import java.util.Objects;

public class Coordinates {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }


    public Coordinates(){
        this(0,0);
    }

    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void incrementX(int value){
        this.x += value;
    }

    public void incrementY(int value){
        this.y += value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return this.x == that.getX() && this.y == that.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
