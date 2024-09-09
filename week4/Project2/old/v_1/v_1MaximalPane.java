package v_1;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;

public class v_1MaximalPane extends Pane {
    private ArrayList<v_1Point> points;

    public v_1MaximalPane(ArrayList<v_1Point> points) {
        this.points = points;
        setPrefSize(500, 500);
        findAndDrawMaximalPoints();
        
        setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Add point on left-click
                points.add(new v_1Point(event.getX(), event.getY()));
            } else if (event.getButton() == MouseButton.SECONDARY) {
                // Remove closest point on right-click
                v_1Point closestPoint = findClosestPoint(event.getX(), event.getY());
                points.remove(closestPoint);
            }
            findAndDrawMaximalPoints();
        });
    }

    private v_1Point findClosestPoint(double x, double y) {
        v_1Point closest = null;
        double minDistance = Double.MAX_VALUE;
        for (v_1Point p : points) {
            double distance = Math.hypot(p.getX() - x, p.getY() - y);
            if (distance < minDistance) {
                minDistance = distance;
                closest = p;
            }
        }
        return closest;
    }

    private void findAndDrawMaximalPoints() {
        getChildren().clear(); // Clear previous points and lines
        Collections.sort(points); // Sort points based on x-coordinates
    
        ArrayList<v_1Point> maximalPoints = new ArrayList<>();
        v_1Point maxPoint = null;
    
        // Start from the rightmost point and work left, keeping track of the largest y-coordinate
        for (int i = points.size() - 1; i >= 0; i--) {
            v_1Point p = points.get(i);
            if (maxPoint == null || p.getY() > maxPoint.getY()) {
                maximalPoints.add(p);
                maxPoint = p; // Update maxPoint to the current point
            }
        }
    
        // Reverse the order of maximal points to draw them from left to right
        Collections.reverse(maximalPoints);
    
        // Draw points
        for (v_1Point p : points) {
            getChildren().add(new Circle(p.getX(), p.getY(), 5));
        }
    
        // Draw lines connecting maximal points
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            v_1Point p1 = maximalPoints.get(i);
            v_1Point p2 = maximalPoints.get(i + 1);
            getChildren().add(new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY()));
        }
    }
    
}
