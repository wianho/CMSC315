package v_2;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collections;

public class v_2MaximalPointsPane extends Pane {
    private ArrayList<v_2Point> points;

    // Constructor: supplied an array list of points
    public v_2MaximalPointsPane(ArrayList<v_2Point> points) {
        this.points = points;
        setPrefSize(500, 500); // Set the pane size to 500x500
        drawMaximalPoints();

        // Handle mouse clicks to add/remove points
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) { // Left click to add point
                points.add(new v_2Point(event.getX(), event.getY()));
            } else if (event.getButton() == MouseButton.SECONDARY) { // Right click to remove closest point
                v_2Point closest = findClosestPoint(event.getX(), event.getY());
                points.remove(closest);
            }
            drawMaximalPoints(); // Recompute and redraw the maximal points
        });
    }

    // Private method to find and draw the maximal points and connect them with lines
    private void drawMaximalPoints() {
        getChildren().clear(); // Clear the pane of previous points and lines
        
        // Sort points by x-coordinate (ascending, from left to right)
        Collections.sort(points); 

        ArrayList<v_2Point> maximalPoints = new ArrayList<>();
        double maxY = Double.MIN_VALUE;

        // Iterate from rightmost point to leftmost to find maximal points
        for (int i = points.size() - 1; i >= 0; i--) {
            v_2Point p = points.get(i);
            // Mark the point as maximal if it has no other point to the right with a higher y-coordinate
            if (p.getY() >= maxY) {  
                maximalPoints.add(p);
                maxY = p.getY(); // Update maxY to keep track of the highest point found so far
                System.out.println("Maximal Point: " + p); // Debugging: Print the maximal point
            }
        }

        // Sort maximal points by x ascending for proper connection order
        Collections.sort(maximalPoints);

        // Draw all points
        for (v_2Point point : points) {
            Circle circle = new Circle(point.getX(), point.getY(), 5, Color.BLACK);
            getChildren().add(circle);
        }

        // Draw lines connecting maximal points
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            v_2Point p1 = maximalPoints.get(i);
            v_2Point p2 = maximalPoints.get(i + 1);
            Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            getChildren().add(line);
        }
    }

    // Helper function to find the closest point to the clicked location
    private v_2Point findClosestPoint(double x, double y) {
        v_2Point closest = null;
        double minDistance = Double.MAX_VALUE;

        for (v_2Point p : points) {
            double distance = Math.hypot(p.getX() - x, p.getY() - y);
            if (distance < minDistance) {
                minDistance = distance;
                closest = p;
            }
        }
        return closest;
    }
}
