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
            if (event.getButton() == MouseButton.PRIMARY) {
                points.add(new Point(event.getX(), event.getY())); // Add point
                System.out.println("Point added: " + event.getX() + ", " + event.getY()); // Log added point
            } else if (event.getButton() == MouseButton.SECONDARY) {
                Point closest = findClosestPoint(event.getX(), event.getY());
                if (closest != null) {
                    points.remove(closest);
                    System.out.println("Point removed: " + closest); // Log removed point
                }
            }
            drawMaximalPoints(); // Redraw the points
        });
    }

    // Method to find and draw maximal points
    private void drawMaximalPoints() {
        // Clear any existing graphical elements (points and lines)
        getChildren().clear();
        
        // Draw circles for all points in the 'points' list
        for (Point point : points) {
            Circle circle = new Circle(point.getX(), point.getY(), 5, Color.BLACK);
            getChildren().add(circle); // Add the circle representing the point to the pane
        }
        
        // Compute maximal points
        ArrayList<Point> maximalPoints = findMaximalPoints();
    
        // Sort maximal points by x-coordinate in ascending order (left to right)
        Collections.sort(maximalPoints, (p1, p2) -> Double.compare(p1.getX(), p2.getX()));
    
        // Debugging: Print the sorted maximal points
        System.out.println("Sorted maximal points (left to right):");
        for (Point p : maximalPoints) {
            System.out.println("Point: (" + p.getX() + ", " + p.getY() + ")");
        }
    
        // Draw L-shaped lines connecting maximal points (horizontal first, then vertical)
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point p1 = maximalPoints.get(i);
            Point p2 = maximalPoints.get(i + 1);
    
            // Horizontal line from p1 to p2 (x changes, y stays the same)
            Line horizontalLine = new Line(p1.getX(), p1.getY(), p2.getX(), p1.getY());
            getChildren().add(horizontalLine);
    
            // Vertical line from p1's y to p2's y (x stays the same, y changes)
            Line verticalLine = new Line(p2.getX(), p1.getY(), p2.getX(), p2.getY());
            getChildren().add(verticalLine);
        }
    }
        

    // Method to find maximal points
    private ArrayList<Point> findMaximalPoints() {
        ArrayList<Point> maximalPoints = new ArrayList<>();
        for (Point p1 : points) {
            boolean isMaximal = true;
            for (Point p2 : points) {
                // A point is not maximal if there exists another point strictly to its right and above it
                if (p2.getX() > p1.getX() && p2.getY() >= p1.getY()) {
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
