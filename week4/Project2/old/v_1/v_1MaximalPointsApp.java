package v_1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class v_1MaximalPointsApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        ArrayList<v_1Point> points = readPointsFromFile("points.txt");
        v_1MaximalPane pane = new v_1MaximalPane(points);
        Scene scene = new Scene(pane);
        
        primaryStage.setTitle("Maximal Points");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ArrayList<v_1Point> readPointsFromFile(String filename) {
        ArrayList<v_1Point> points = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextDouble()) {
                double x = scanner.nextDouble();
                double y = scanner.nextDouble();
                points.add(new v_1Point(x, y));
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
