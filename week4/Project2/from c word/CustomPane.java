import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.input.MouseButton;


public class CustomPane extends Pane {
    private ObservableList<Point> points;
    
    public CustomPane(ObservableList<Point> points) {
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
        for (int i = 0; i < maximalPoints.size() - 1; i++) {
            Point p1 = maximalPoints.get(i);
            Point p2 = maximalPoints.get(i + 1);
            Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
            this.getChildren().add(line);
        }
    }

    private ObservableList<Point> findMaximalPoints() {
        FXCollections.sort(points);
        ObservableList<Point> result = FXCollections.observableArrayList();
        Point current = points.get(0);
        result.add(current);
        for (Point p : points) {
            if (p.isBelowAndLeftOf(current)) {
                result.add(p);
                current = p;
            }
        }
        return result;
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