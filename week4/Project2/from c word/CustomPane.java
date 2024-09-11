import javafx.collections.ObservableList;  // Import ObservableList to manage dynamic lists of points
import javafx.collections.FXCollections;  // Import FXCollections to manipulate ObservableLists
import javafx.scene.layout.Pane;  // Import Pane to create the custom UI component
import javafx.scene.input.MouseEvent;  // Import MouseEvent for handling mouse input events
import javafx.scene.shape.Circle;  // Import Circle to represent points in the UI
import javafx.scene.shape.Line;  // Import Line to draw connections between points
import javafx.scene.input.MouseButton;  // Import MouseButton to differentiate left and right mouse clicks

public class CustomPane extends Pane {
    private ObservableList<Point> points;  // A dynamic list to store points displayed on the pane
    
    // Constructor to initialize the Pane with a list of points
    public CustomPane(ObservableList<Point> points) {
        this.points = points;  // Store the points passed in the constructor
        drawInitialPoints();  // Draw the initial set of points and lines
        this.setOnMouseClicked(this::handleMouseClick);  // Set an event handler for mouse clicks
        this.setPrefSize(500, 500);  // Set the preferred size of the pane
    }

    // Method to clear the pane and redraw the initial points and lines
    private void drawInitialPoints() {
        this.getChildren().clear();  // Clear the existing points and lines on the pane

        // Draw each point as a small circle on the pane
        for (Point point : points) {
            Circle circle = new Circle(point.getX(), point.getY(), 5);  // Create a circle at the point's location
            this.getChildren().add(circle);  // Add the circle to the pane
        }

        drawLines();  // Draw the lines connecting the maximal points
    }

    // Method to draw lines between the maximal points
    private void drawLines() {
        ObservableList<Point> maximalPoints = findMaximalPoints();  // Find the set of maximal points

        // Draw lines connecting each pair of consecutive maximal points
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point p1 = maximalPoints.get(i);  // Get the first point in the pair
            Point p2 = maximalPoints.get(i + 1);  // Get the next point in the pair

            // Draw a line connecting p1 and p2
            Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            this.getChildren().add(line);  // Add the line to the pane
        }
    }

    // Method to find the set of maximal points
    // Maximal points are those that are not dominated by any other point
    private ObservableList<Point> findMaximalPoints() {
        FXCollections.sort(points);  // Sort the points by their x-coordinate using the compareTo method
        ObservableList<Point> result = FXCollections.observableArrayList();  // List to store the maximal points
        Point current = points.get(0);  // Start with the first point in the sorted list
        result.add(current);  // Add the first point to the maximal set

        // Iterate over the points and find those that are maximal
        for (Point p : points) {
            // If the current point is dominated (below and to the left), add it to the maximal set
            if (p.isBelowAndLeftOf(current)) {
                result.add(p);
                current = p;  // Update the current maximal point
            }
        }

        return result;  // Return the list of maximal points
    }

    // Method to handle mouse click events on the pane
    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {  // If the left mouse button is clicked
            points.add(new Point(event.getX(), event.getY()));  // Add a new point at the click location
        } else if (event.getButton() == MouseButton.SECONDARY) {  // If the right mouse button is clicked
            Point toRemove = findClosestPoint(event.getX(), event.getY());  // Find the closest point to remove
            points.remove(toRemove);  // Remove the closest point
        }
        drawInitialPoints();  // Recompute and redraw the points and lines
    }

    // Method to find the closest point to a given (x, y) location
    private Point findClosestPoint(double x, double y) {
        Point closest = null;  // Variable to store the closest point
        double minDistance = Double.MAX_VALUE;  // Initialize the minimum distance as the largest possible value

        // Iterate through the points and find the closest one to the given (x, y) coordinates
        for (Point point : points) {
            // Calculate the Euclidean distance between the point and the given (x, y)
            double distance = Math.sqrt(Math.pow(point.getX() - x, 2) + Math.pow(point.getY() - y, 2));

            // If this point is closer than the current closest, update the closest point
            if (distance < minDistance) {
                closest = point;
                minDistance = distance;
            }
        }

        return closest;  // Return the closest point
    }
}
