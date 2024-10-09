/*
 * Name: William Holt
 * Project: CMSC 315 - Graph Connectivity and Cycles
 * Date: 08-OCT-2024
 * Description: This class defines a vertex with attributes for the vertex label, x-coordinate,
 * and y-coordinate. It includes methods to retrieve these attributes. The class is immutable.
 */

 public class Vertex {
    private final String name; // Label of the vertex (e.g., "A", "B")
    private final double x, y; // Coordinates of the vertex

    /*
     * Constructor: Initializes the vertex with a label and coordinates.
     */
    public Vertex(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /*
     * getName: Returns the name/label of the vertex.
     */
    public String getName() {
        return name;
    }

    /*
     * getX: Returns the x-coordinate of the vertex.
     */
    public double getX() {
        return x;
    }

    /*
     * getY: Returns the y-coordinate of the vertex.
     */
    public double getY() {
        return y;
    }
}
