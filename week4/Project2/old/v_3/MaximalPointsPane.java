import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;

public class MaximalPointsPane extends Pane {
    private ArrayList<Point> points;

    // Constructor: supplied an array list of points
    public MaximalPointsPane(ArrayList<Point> points) {
        this.points = points;
        setPrefSize(500, 500); // Set the pane size to 500x500
        drawMaximalPoints();

        // Handle mouse clicks to add/remove points
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) { // Left click to add point
                points.add(new Point(event.getX(), event.getY()));
            } else if (event.getButton() == MouseButton.SECONDARY) { // Right click to remove closest point
                Point closest = findClosestPoint(event.getX(), event.getY());
                points.remove(closest);
            }
            drawMaximalPoints(); // Recompute and redraw the maximal points
        });
    }

    // Private method to find and draw the maximal points and connect them with lines
    private void drawMaximalPoints() {
        getChildren().clear(); // Clear the pane of previous points and lines
        
        ArrayList<Point> maximalPoints = new ArrayList<>();
        
        // Iterate over each point to see if it's a maximal point
        for (Point p : points) {
            boolean isMaximal = true;
            
            // Check if there's any point to the right and above
            for (Point other : points) {
                if (other.getX() > p.getX() && other.getY() > p.getY()) {
                    isMaximal = false;
                    break;  // No need to keep checking, it's not maximal
                }
            }
            
            if (isMaximal) {
                maximalPoints.add(p);
                System.out.println("Maximal Point: " + p); // Debugging: Print the maximal point
            }
        }

        // Sort maximal points by x ascending for proper connection order
        Collections.sort(maximalPoints);

        // Draw all points
        for (Point point : points) {
            Circle circle = new Circle(point.getX(), point.getY(), 5, Color.BLACK);
            getChildren().add(circle);
        }

        // Draw lines connecting maximal points
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point p1 = maximalPoints.get(i);
            Point p2 = maximalPoints.get(i + 1);
            Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            getChildren().add(line);
        }
    }

    // Helper function to find the closest point to the clicked location
    private Point findClosestPoint(double x, double y) {
        Point closest = null;
        double minDistance = Double.MAX_VALUE;

        for (Point p : points) {
            double distance = Math.hypot(p.getX() - x, p.getY() - y);
            if (distance < minDistance) {
                minDistance = distance;
                closest = p;
            }
        }
        return closest;
    }
}
