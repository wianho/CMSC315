/*
 * Name: William Holt
 * Project: CMSC 315 - Graph Connectivity and Cycles
 * Date: 08-OCT-2024
 * Description: This class defines an undirected graph using an adjacency list. It includes methods
 * to add vertices, add edges, check for graph connectivity, detect cycles, and perform depth-first
 * and breadth-first search algorithms.
 */

 import java.util.*;

 public class Graph {
     private final Map<Vertex, List<Vertex>> adjacencyList; // Stores the graph using an adjacency list representation
 
     /*
      * Constructor: Initializes an empty graph.
      */
     public Graph() {
         this.adjacencyList = new HashMap<>();
     }
 
     /*
      * addVertex: Adds a vertex to the graph.
      * @param v: Vertex to be added.
      */
     public void addVertex(Vertex v) {
         adjacencyList.putIfAbsent(v, new ArrayList<>());
     }
 
     /*
      * addEdge: Adds an undirected edge between two vertices in the graph.
      * @param v1: First vertex.
      * @param v2: Second vertex.
      */
     public void addEdge(Vertex v1, Vertex v2) {
         adjacencyList.get(v1).add(v2);
         adjacencyList.get(v2).add(v1);
     }
 
     /*
      * hasCycles: Checks whether the graph contains any cycles.
      * Uses depth-first search (DFS) to detect cycles.
      * @return: true if the graph has cycles, false otherwise.
      */
     public boolean hasCycles() {
         Set<Vertex> visited = new HashSet<>();
         for (Vertex v : adjacencyList.keySet()) {
             if (!visited.contains(v)) {
                 if (dfsHasCycle(v, null, visited)) {
                     return true;
                 }
             }
         }
         return false;
     }
 
     /*
      * dfsHasCycle: Recursive helper method for cycle detection.
      * @param v: Current vertex.
      * @param parent: The vertex from which we reached the current vertex.
      * @param visited: Set of visited vertices.
      * @return: true if a cycle is detected, false otherwise.
      */
     private boolean dfsHasCycle(Vertex v, Vertex parent, Set<Vertex> visited) {
         visited.add(v);
         for (Vertex neighbor : adjacencyList.get(v)) {
             if (!visited.contains(neighbor)) {
                 if (dfsHasCycle(neighbor, v, visited)) {
                     return true;
                 }
             } else if (!neighbor.equals(parent)) {
                 return true; // A back edge is found, indicating a cycle
             }
         }
         return false;
     }
 
     /*
      * isConnected: Checks if the graph is connected (i.e., all vertices are reachable).
      * @return: true if the graph is connected, false otherwise.
      */
     public boolean isConnected() {
         if (adjacencyList.isEmpty()) return true; // Empty graph is trivially connected
         Set<Vertex> visited = new HashSet<>();
         dfs(adjacencyList.keySet().iterator().next(), visited);
         return visited.size() == adjacencyList.size(); // Check if all vertices were visited
     }
 
     /*
      * dfs: Recursive depth-first search (DFS) to explore all reachable vertices.
      * @param v: Current vertex.
      * @param visited: Set of visited vertices.
      */
     private void dfs(Vertex v, Set<Vertex> visited) {
         visited.add(v);
         for (Vertex neighbor : adjacencyList.get(v)) {
             if (!visited.contains(neighbor)) {
                 dfs(neighbor, visited);
             }
         }
     }
 
     /*
      * depthFirstSearch: Performs depth-first search (DFS) starting from a given vertex.
      * @param start: Starting vertex.
      * @return: List of vertices visited in DFS order.
      */
     public List<Vertex> depthFirstSearch(Vertex start) {
         List<Vertex> result = new ArrayList<>();
         Set<Vertex> visited = new HashSet<>();
         dfsTraversal(start, visited, result);
         return result;
     }
 
     /*
      * dfsTraversal: Recursive DFS to collect the vertices in DFS order.
      * @param v: Current vertex.
      * @param visited: Set of visited vertices.
      * @param result: List to store the order of visited vertices.
      */
     private void dfsTraversal(Vertex v, Set<Vertex> visited, List<Vertex> result) {
         visited.add(v);
         result.add(v);
         for (Vertex neighbor : adjacencyList.get(v)) {
             if (!visited.contains(neighbor)) {
                 dfsTraversal(neighbor, visited, result);
             }
         }
     }
 
     /*
      * breadthFirstSearch: Performs breadth-first search (BFS) starting from a given vertex.
      * @param start: Starting vertex.
      * @return: List of vertices visited in BFS order.
      */
     public List<Vertex> breadthFirstSearch(Vertex start) {
         List<Vertex> result = new ArrayList<>();
         Set<Vertex> visited = new HashSet<>();
         Queue<Vertex> queue = new LinkedList<>();
         queue.add(start);
         visited.add(start);
 
         while (!queue.isEmpty()) {
             Vertex v = queue.poll();
             result.add(v);
             for (Vertex neighbor : adjacencyList.get(v)) {
                 if (!visited.contains(neighbor)) {
                     queue.add(neighbor);
                     visited.add(neighbor);
                 }
             }
         }
         return result;
     }
 }
 