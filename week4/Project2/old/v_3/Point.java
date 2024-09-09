public class Point implements Comparable<Point> {
    private final double x;
    private final double y;

    // Constructor that initializes x and y
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Get the x-coordinate
    public double getX() {
        return x;
    }

    // Get the y-coordinate
    public double getY() {
        return y;
    }

    // Check if another point is below and to the left
    public boolean isBelowAndToLeft(Point other) {
        return this.x > other.x && this.y > other.y;
    }

    // Compare by x-coordinate only
    @Override
    public int compareTo(Point other) {
        return Double.compare(this.x, other.x);
    }

    // ToString for debugging purposes
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
