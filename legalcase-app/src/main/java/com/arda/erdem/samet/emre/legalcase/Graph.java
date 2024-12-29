/**
 * @file Graph.java
 * @brief Represents an undirected graph using an adjacency list.
 *
 * @details
 * This class provides a structure for managing a graph, including the vertices, 
 * adjacency lists for representing connections, and visited flags for traversal algorithms.
 * It is designed for efficient graph representation and operations like traversal and connectivity checks.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note Useful for algorithms requiring adjacency list-based graph representations.
 */

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
 */
public class Graph {
    int numVertices;           // Düğüm sayısı
    GraphNode[] adjLists;      // Bağlı liste
    boolean[] visited;         // Ziyaret kontrolü

    /**
     * @brief Constructor for initializing a Graph.
     * 
     * @details Creates a graph with the specified number of vertices, initializing 
     * the adjacency lists and visited array. Initially, all vertices are unvisited 
     * and have no connections.
     * 
     * @param vertices The number of vertices in the graph.
     */
    public Graph(int vertices) {
        this.numVertices = vertices;
        this.adjLists = new GraphNode[vertices];
        this.visited = new boolean[vertices];

        // Başlangıçta tüm düğümler boş ve ziyaret edilmemiş
        for (int i = 0; i < vertices; i++) {
            adjLists[i] = null;
            visited[i] = false;
        }
    }
}
