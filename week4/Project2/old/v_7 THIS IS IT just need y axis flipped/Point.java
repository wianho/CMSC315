public class Point implements Comparable<Point> {
    private final double x;
    private final double y;

    // Constructor to initialize a Point object
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Getter method for the x-coordinate
    public double getX() {
        return x;
    }

    // Getter method for the y-coordinate
    public double getY() {
        return y;
    }

    // Checks if this point is below and to the left of another point
    public boolean isBelowAndLeftOf(Point other) {
        return this.x < other.x && this.y < other.y;
    }

    // Compares points based on their x-coordinates
    @Override
    public int compareTo(Point other) {
        return Double.compare(this.x, other.x);
    }
}
