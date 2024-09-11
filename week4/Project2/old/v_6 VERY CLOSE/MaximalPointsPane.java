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

    // Draws the initial set of points
    private void drawInitialPoints() {
        this.getChildren().clear();
        for (Point point : points) {
            Circle circle = new Circle(point.getX(), point.getY(), 5);
            this.getChildren().add(circle);
        }
        drawLines();
    }

    private void drawLines() {
        // Find the maximal points
        ObservableList<Point> maximalPoints = findMaximalPoints();
        
        // Height of the pane, used to flip the y-coordinate
        double paneHeight = this.getHeight();  // Dynamically get the height of the pane
        
        // Debugging: Print the maximal points to the console
        System.out.println("Maximal points:");
        for (Point p : maximalPoints) {
            System.out.println("Point: (" + p.getX() + ", " + p.getY() + ")");
        }
    
        // Sort maximal points by x and then by y if needed
        FXCollections.sort(maximalPoints, (p1, p2) -> {
            int xCompare = Double.compare(p1.getX(), p2.getX());
            if (xCompare == 0) {
                return Double.compare(p1.getY(), p2.getY());
            }
            return xCompare;
        });
    
        // Draw lines between sorted points
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point p1 = maximalPoints.get(i);
            Point p2 = maximalPoints.get(i + 1);
    
            // Adjust the y-coordinate by subtracting from the pane height to flip the axis
            double adjustedY1 = paneHeight - p1.getY();
            double adjustedY2 = paneHeight - p2.getY();
    
            // Draw horizontal and vertical lines using adjusted y-coordinates
            Line horizontalLine = new Line(p1.getX(), adjustedY1, p2.getX(), adjustedY1);
            Line verticalLine = new Line(p2.getX(), adjustedY1, p2.getX(), adjustedY2);
    
            this.getChildren().addAll(horizontalLine, verticalLine);
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
        if (event.getButton() == MouseButton.PRIMARY) {
            points.add(new Point(event.getX(), event.getY()));
        } else if (event.getButton() == MouseButton.SECONDARY) {
            Point toRemove = findClosestPoint(event.getX(), event.getY());
            points.remove(toRemove);
        }
        drawInitialPoints();
    }

    // Finds the closest point to a given (x, y) coordinate
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
