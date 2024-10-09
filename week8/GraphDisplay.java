/*
 * Name: William Holt
 * Project: CMSC 315 - Graph Connectivity and Cycles
 * Date: 08-OCT-2024
 * Description: This class extends JavaFX's Pane and is responsible for visually displaying the
 * graph. It allows users to add vertices by clicking on the screen and draws edges between vertices.
 */

 import javafx.scene.layout.Pane;
 import javafx.scene.paint.Color;
 import javafx.scene.shape.Circle;
 import javafx.scene.shape.Line;
 import javafx.scene.text.Text;
 import java.util.ArrayList;
 import java.util.List;
 
 public class GraphDisplay extends Pane {
     private final Graph graph; // The graph to be displayed
     private char vertexLabel = 'A';  // Label for new vertices
     private final List<Vertex> vertices; // List of vertices added to the graph
 
     /*
      * Constructor: Initializes the GraphDisplay and handles mouse clicks to add vertices.
      * @param graph: The graph that will be displayed and manipulated.
      */
     public GraphDisplay(Graph graph) {
         this.graph = graph;
         this.vertices = new ArrayList<>();
 
         setOnMouseClicked(e -> {
             double x = e.getX();
             double y = e.getY();
             if (vertexLabel <= 'Z') {
                 Vertex v = new Vertex(String.valueOf(vertexLabel), x, y);
                 vertices.add(v);
                 graph.addVertex(v);
                 drawVertex(v);
                 vertexLabel++;
             }
         });
     }
 
     /*
      * getVertices: Returns the list of vertices currently in the graph.
      */
     public List<Vertex> getVertices() {
         return vertices;
     }
 
     /*
      * drawVertex: Draws a vertex on the screen at the given coordinates.
      * @param v: The vertex to be drawn.
      */
     private void drawVertex(Vertex v) {
         Circle circle = new Circle(v.getX(), v.getY(), 15, Color.LIGHTBLUE);
         Text label = new Text(v.getX() - 5, v.getY() + 5, v.getName());
         getChildren().addAll(circle, label); // Adds the vertex and its label to the Pane
     }
 
     /*
      * drawEdge: Draws an edge between two vertices.
      * @param v1: First vertex.
      * @param v2: Second vertex.
      */
     public void drawEdge(Vertex v1, Vertex v2) {
         Line line = new Line(v1.getX(), v1.getY(), v2.getX(), v2.getY());
         getChildren().add(line); // Adds the edge to the Pane
     }
 }
 