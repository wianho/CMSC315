import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MaximalPointsPane extends Pane {
    private ObservableList<Point> points;

    public MaximalPointsPane(ObservableList<Point> points) {
        this.points = points;
        drawInitialPoints();
        this.setOnMouseClicked(this::handleMouseClick);
        this.setPrefSize(500, 500);
    }

    private void drawInitialPoints() {
        this.getChildren().clear();
        for (Point point : points) {
            Circle circle = new Circle(point.getX(), point.getY(), 5);
            this.getChildren().add(circle);
        }
        drawLines();
    }

    private void drawLines() {
        ObservableList<Point> maximalPoints = findMaximalPoints();
        
        // Debugging: Print maximal points to verify the result
        System.out.println("Maximal points:");
        for (Point p : maximalPoints) {
            System.out.println("Point: (" + p.getX() + ", " + p.getY() + ")");
        }

        // Sort maximal points by x-coordinate (left to right)
        FXCollections.sort(maximalPoints);
        
        // Draw L-shaped lines connecting maximal points
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point p1 = maximalPoints.get(i);
            Point p2 = maximalPoints.get(i + 1);

            // Horizontal line from p1 to p2 (x changes, y stays the same)
            Line horizontalLine = new Line(p1.getX(), p1.getY(), p2.getX(), p1.getY());
            this.getChildren().add(horizontalLine);

            // Vertical line from p1's y to p2's y (x stays the same, y changes)
            Line verticalLine = new Line(p2.getX(), p1.getY(), p2.getX(), p2.getY());
            this.getChildren().add(verticalLine);
        }
    }

    private ObservableList<Point> findMaximalPoints() {
        ObservableList<Point> maximalPoints = FXCollections.observableArrayList();
        for (Point p1 : points) {
            boolean isMaximal = true;
            for (Point p2 : points) {
                // A point is not maximal if there exists another point to the right and above it
                if (p2.getX() > p1.getX() && p2.getY() > p1.getY()) {
                    isMaximal = false;
                    break;
                }
            }
            if (isMaximal) {
                maximalPoints.add(p1);
            }
        }
        return maximalPoints;
    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            points.add(new Point(event.getX(), event.getY()));
        } else if (event.getButton() == MouseButton.SECONDARY) {
            Point toRemove = findClosestPoint(event.getX(), event.getY());
            points.remove(toRemove);
        }
        drawInitialPoints();
    }

    private Point findClosestPoint(double x, double y) {
        Point closest = null;
        double minDistance = Double.MAX_VALUE;
        for (Point point : points) {
            double distance = Math.sqrt(Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2));
            if (distance < minDistance) {
                closest = point;
                minDistance = distance;
            }
        }
        return closest;
    }
}
