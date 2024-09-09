import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class MaximalPointsApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Read points from file or hard-code points for testing
        ObservableList<Point> points = readPointsFromFile("points.txt");
        MaximalPointsPane pane = new MaximalPointsPane(points);
        Scene scene = new Scene(pane, 500, 500);
        primaryStage.setTitle("Maximal Points");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ObservableList<Point> readPointsFromFile(String filename) {
        ObservableList<Point> points = FXCollections.observableArrayList();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextDouble()) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                points.add(new Point(x, y));
                System.out.println("Point read from file: (" + x + ", " + y + ")");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return points;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
