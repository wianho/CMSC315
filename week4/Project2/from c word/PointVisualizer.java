import javafx.application.Application;  // Import Application class to create a JavaFX app
import javafx.scene.Scene;  // Import Scene class for setting up the visual display
import javafx.stage.Stage;  // Import Stage class to create the primary window
import javafx.collections.ObservableList;  // Import ObservableList to handle dynamic point collections
import javafx.collections.FXCollections;  // Import FXCollections to create and manipulate ObservableLists

public class PointVisualizer extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Method that initializes the primary stage (window) for the JavaFX application

        // Read the points from a file (or hard-code points for testing)
        ObservableList<Point> points = readPointsFromFile("points.txt");

        // Create an instance of CustomPane, passing in the points
        // CustomPane is responsible for drawing the points and their connections
        CustomPane pane = new CustomPane(points);

        // Create a Scene that will contain the CustomPane
        Scene scene = new Scene(pane);

        // Set the title of the primary window (stage)
        primaryStage.setTitle("Point Visualizer");

        // Attach the scene (containing the points and lines) to the stage
        primaryStage.setScene(scene);

        // Display the stage (window) to the user
        primaryStage.show();
    }

    // Method to read points from a file and return them as an ObservableList
    // For now, this is a placeholder that manually creates a couple of points
    // In the final version, you would implement file reading to populate this list
    private ObservableList<Point> readPointsFromFile(String filename) {
        // Example implementation: returns a list with two points for testing
        return FXCollections.observableArrayList(
            new Point(100, 100),  // Create a point at coordinates (100, 100)
            new Point(200, 200)   // Create a point at coordinates (200, 200)
        );
    }

    // The main method, which launches the JavaFX application
    public static void main(String[] args) {
        launch(args);  // Calls the launch method to start the JavaFX runtime
    }
}
