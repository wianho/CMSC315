import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class MaximalPointsPane extends Pane {
    private ObservableList<Point> points;

    // Constructor to initialize the Pane with a list of points
    public MaximalPointsPane(ObservableList<Point> points) {
        this.points = points;
        drawInitialPoints();
        this.setOnMouseClicked(this::handleMouseClick);
        this.setPrefSize(500, 500);
    }

    private void drawInitialPoints() {
        this.getChildren().clear();  // Clear any existing drawings
    
        double paneHeight = this.getHeight();  // Get the height of the pane
    
        // For each point, create a circle, adjusting the y-coordinate to simulate a Cartesian plane
        for (Point point : points) {
            double adjustedY = paneHeight - point.getY();  // Flip the y-coordinate
            Circle circle = new Circle(point.getX(), adjustedY, 5);  // Radius 5
            this.getChildren().add(circle);
        }
    
        // Draw the lines after placing the points
        drawLines();
    }
    
    
    

    private void drawLines() {
        ObservableList<Point> maximalPoints = findMaximalPoints();
        double paneHeight = this.getHeight();  // Get the height of the pane
    
        // Debug: Print out the maximal points
        System.out.println("Maximal points:");
        for (Point p : maximalPoints) {
            System.out.println("Point: (" + p.getX() + ", " + p.getY() + ")");
        }
    
        // Sort maximal points by x-coordinate, then by y-coordinate if x is the same
        FXCollections.sort(maximalPoints, (p1, p2) -> {
            int xCompare = Double.compare(p1.getX(), p2.getX());
            if (xCompare == 0) {
                return Double.compare(p1.getY(), p2.getY());
            }
            return xCompare;
        });
    
        // Draw stair-step lines between points
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point p1 = maximalPoints.get(i);
            Point p2 = maximalPoints.get(i + 1);
    
            // Adjust the y-coordinates to simulate a Cartesian plane
            double adjustedY1 = paneHeight - p1.getY();  // Adjust y for p1
            double adjustedY2 = paneHeight - p2.getY();  // Adjust y for p2
    
            // 1. Draw horizontal line from p1 to p2
            Line horizontalLine = new Line(p1.getX(), adjustedY1, p2.getX(), adjustedY1);
            this.getChildren().add(horizontalLine);
    
            // 2. Draw vertical line from the end of the horizontal line down to p2
            Line verticalLine = new Line(p2.getX(), adjustedY1, p2.getX(), adjustedY2);
            this.getChildren().add(verticalLine);
        }
    }
    
    

    // Finds the maximal points
    private ObservableList<Point> findMaximalPoints() {
        ObservableList<Point> maximalPoints = FXCollections.observableArrayList();
        for (Point p1 : points) {
            boolean isMaximal = true;
            for (Point p2 : points) {
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

    // Handles mouse clicks for adding or removing points
    private void handleMouseClick(MouseEvent event) {
        double paneHeight = this.getHeight();  // Get the pane height
    
        // Adjust the y-coordinate to match the Cartesian plane simulation
        double adjustedY = paneHeight - event.getY();  // Flip the y-coordinate
    
        if (event.getButton() == MouseButton.PRIMARY) {
            // Add a new point when left-clicking
            points.add(new Point(event.getX(), adjustedY));
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // Remove the closest point when right-clicking
            Point toRemove = findClosestPoint(event.getX(), adjustedY);  // Use adjustedY here
            if (toRemove != null) {
                points.remove(toRemove);
            }
        }
    
        // Redraw the points and lines
        drawInitialPoints();
    }
    
    

    // Finds the closest point to a given (x, y) coordinate
    private Point findClosestPoint(double x, double y) {
        Point closest = null;
        double minDistance = Double.MAX_VALUE;
    
        // Loop through each point to find the closest one
        for (Point point : points) {
            double distance = Math.sqrt(Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2));
    
            if (distance < minDistance) {
                closest = point;
                minDistance = distance;
            }
        }
    
        return closest;  // Return the closest point
    }
    
}
