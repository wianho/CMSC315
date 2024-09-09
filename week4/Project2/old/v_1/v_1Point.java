package v_1;
public class v_1Point implements Comparable<v_1Point> {
    private final double x;
    private final double y;

    public v_1Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    // Method to check if the point is below and to the left
    public boolean isBelowAndToLeft(v_1Point other) {
        return this.x < other.x && this.y < other.y;
    }

    @Override
    public int compareTo(v_1Point other) {
        return Double.compare(this.x, other.x);
    }
}
