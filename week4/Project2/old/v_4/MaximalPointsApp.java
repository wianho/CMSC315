import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MaximalPointsApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ArrayList<Point> points = readPointsFromFile("points.txt"); // Reading points from the file
        MaximalPointsPane pane = new MaximalPointsPane(points); // Create pane with initial points
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Maximal Points");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to read points from the input file

    private ArrayList<Point> readPointsFromFile(String filename) {
        ArrayList<Point> points = new ArrayList<>();
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
