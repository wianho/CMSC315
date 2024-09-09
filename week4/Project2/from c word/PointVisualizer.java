import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


public class PointVisualizer extends Application {

    @Override
    public void start(Stage primaryStage) {
        ObservableList<Point> points = readPointsFromFile("points.txt");
        CustomPane pane = new CustomPane(points);
        Scene scene = new Scene(pane);

        primaryStage.setTitle("Point Visualizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Point> readPointsFromFile(String filename) {
        // Implement file reading logic to populate points
        // Example return statement:
        return FXCollections.observableArrayList(
            new Point(100, 100), new Point(200, 200)
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}