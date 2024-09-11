public class Point implements Comparable<Point> {
    // The x and y coordinates of the point, declared as final to make the class immutable
    private final double x;
    private final double y;

    // Constructor to initialize the Point with x and y coordinates
    public Point(double x, double y) {
        this.x = x;  // Assign the x-coordinate
        this.y = y;  // Assign the y-coordinate
    }

    // Getter method to return the x-coordinate of the point
    public double getX() {
        return x;
    }

    // Getter method to return the y-coordinate of the point
    public double getY() {
        return y;
    }

    // Method to determine if this point is below and to the left of another point
    public boolean isBelowAndLeftOf(Point other) {
        // Returns true if this point's x is smaller than the other point's x
        // and this point's y is smaller than the other point's y
        return this.x < other.x && this.y < other.y;
    }

    // Overriding the compareTo method from the Comparable interface
    // Compares two points based on their x-coordinate
    @Override
    public int compareTo(Point other) {
        // Uses Double.compare to compare the x-coordinates of this point and the other point
        // Double.compare handles floating-point comparisons accurately
        return Double.compare(this.x, other.x);
    }
}
