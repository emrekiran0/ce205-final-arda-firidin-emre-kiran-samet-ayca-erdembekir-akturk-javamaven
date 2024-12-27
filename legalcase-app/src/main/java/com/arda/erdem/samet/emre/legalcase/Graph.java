package com.arda.erdem.samet.emre.legalcase;

/**
 * Represents an undirected graph using an adjacency list.
 * This class provides the foundational structure for managing a graph, including
 * vertices, adjacency lists, and visited flags for traversal algorithms.
 *
 * @fields
 * - `numVertices` (int): The number of vertices in the graph.
 * - `adjLists` (GraphNode[]): An array of adjacency lists, where each index represents a vertex
 *   and the list contains its connected vertices.
 * - `visited` (boolean[]): An array to track whether a vertex has been visited during traversal.
 *
 * @constructor
 * - `Graph(int vertices)`: Initializes the graph with the specified number of vertices.
 *   @param vertices The number of vertices in the graph.
 *   @note All adjacency lists are initially empty, and all vertices are marked as unvisited.
 *
 * @example
 * ```java
 * int numVertices = 5;
 * Graph graph = new Graph(numVertices);
 * System.out.println("Graph initialized with " + graph.numVertices + " vertices.");
 * ```
 */
public class Graph {
    int numVertices;           
    GraphNode[] adjLists;      
    boolean[] visited;         

    public Graph(int vertices) {
        this.numVertices = vertices;
        this.adjLists = new GraphNode[vertices];
        this.visited = new boolean[vertices];

        for (int i = 0; i < vertices; i++) {
            adjLists[i] = null;
            visited[i] = false;
        }
    }
}
