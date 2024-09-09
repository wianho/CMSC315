package v_2;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class v_2MaximalPointsApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ArrayList<v_2Point> points = readPointsFromFile("points.txt"); // Reading points from the file
        v_2MaximalPointsPane pane = new v_2MaximalPointsPane(points); // Create pane with initial points
        Scene scene = new Scene(pane);
        primaryStage.setTitle("Maximal Points");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to read points from the input file
    private ArrayList<v_2Point> readPointsFromFile(String filename) {
        ArrayList<v_2Point> points = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextDouble()) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                points.add(new v_2Point(x, y));
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
