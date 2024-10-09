/*
 * Name: William Holt
 * Project: CMSC 315 - Graph Connectivity and Cycles
 * Date: 08-OCT-2024
 * Description: This class contains the main method and sets up the JavaFX GUI. It manages user
 * interaction, allowing users to add edges, check for connectivity, detect cycles, and perform
 * depth-first and breadth-first searches.
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GraphApp extends Application {
    private Graph graph = new Graph(); // Graph object to hold vertices and edges
    private GraphDisplay graphDisplay = new GraphDisplay(graph); // GraphDisplay to visually represent the graph

    /*
     * main: Launches the JavaFX application.
     * @param args: Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /*
     * start: Sets up the JavaFX GUI, handling user input for adding edges and checking graph properties.
     * @param primaryStage: The main stage for the JavaFX application.
     */
    @Override
    public void start(Stage primaryStage) {
        BorderPane pane = new BorderPane();
        pane.setCenter(graphDisplay); // Set the GraphDisplay in the center of the pane

        TextField vertex1Field = new TextField(); // Text field for the first vertex
        TextField vertex2Field = new TextField(); // Text field for the second vertex
        Button addEdgeBtn = new Button("Add Edge"); // Button to add an edge between two vertices

        Button isConnectedBtn = new Button("Is Connected?"); // Button to check if the graph is connected
        Button hasCyclesBtn = new Button("Has Cycles?"); // Button to check if the graph has cycles
        Button dfsBtn = new Button("DFS"); // Button to perform a depth-first search
        Button bfsBtn = new Button("BFS"); // Button to perform a breadth-first search

        HBox controlBox = new HBox(10, vertex1Field, vertex2Field, addEdgeBtn, isConnectedBtn, hasCyclesBtn, dfsBtn, bfsBtn); 
        pane.setBottom(controlBox); // Add control buttons and text fields to the bottom of the pane

        /*
         * Event handler for adding an edge between two vertices specified in the text fields.
         */
        addEdgeBtn.setOnAction(e -> {
            String v1Name = vertex1Field.getText();
            String v2Name = vertex2Field.getText();
            Vertex v1 = findVertexByName(v1Name);
            Vertex v2 = findVertexByName(v2Name);

            if (v1 != null && v2 != null) {
                graph.addEdge(v1, v2); // Add edge between the two vertices
                graphDisplay.drawEdge(v1, v2); // Draw the edge on the GUI
            }
        });

        /*
         * Event handler for checking if the graph is connected.
         */
        isConnectedBtn.setOnAction(e -> {
            boolean isConnected = graph.isConnected(); 
            System.out.println("Graph is connected: " + isConnected); // Print the result
        });

        /*
         * Event handler for checking if the graph contains any cycles.
         */
        hasCyclesBtn.setOnAction(e -> {
            boolean hasCycles = graph.hasCycles();
            System.out.println("Graph has cycles: " + hasCycles); // Print the result
        });

        /*
         * Event handler for performing a depth-first search starting at vertex 'A'.
         */
        dfsBtn.setOnAction(e -> {
            System.out.println("DFS: " + graph.depthFirstSearch(findVertexByName("A"))); // Perform DFS and print the result
        });

        /*
         * Event handler for performing a breadth-first search starting at vertex 'A'.
         */
        bfsBtn.setOnAction(e -> {
            System.out.println("BFS: " + graph.breadthFirstSearch(findVertexByName("A"))); // Perform BFS and print the result
        });

        Scene scene = new Scene(pane, 800, 600); // Create the scene
        primaryStage.setTitle("Graph Application"); // Set the title of the window
        primaryStage.setScene(scene); // Set the scene on the stage
        primaryStage.show(); // Display the stage
    }

    /*
     * findVertexByName: Searches for a vertex in the graph by its name.
     * @param name: The name of the vertex to search for.
     * @return: The vertex if found, null otherwise.
     */
    private Vertex findVertexByName(String name) {
        for (Vertex v : graphDisplay.getVertices()) {
            if (v.getName().equals(name)) {
                return v; // Return the vertex if the name matches
            }
        }
        return null; // Return null if no vertex with the given name is found
    }
}

