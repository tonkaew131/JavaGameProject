package core;

public class Point<T> {
    T x;
    T y;

    public Point() {
        this.x = null;
        this.y = null;
    }

    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }
}