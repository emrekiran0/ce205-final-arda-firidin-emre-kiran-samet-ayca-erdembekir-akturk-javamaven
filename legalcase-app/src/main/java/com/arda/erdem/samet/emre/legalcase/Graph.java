/**
 * @brief Package containing the classes and logic for the Legal Case Management System.
 * 
 * @details This package, `com.arda.erdem.samet.emre.legalcase`, is the primary namespace
 * for all classes, data structures, and functionalities involved in the Legal Case Management System. 
 * It includes classes such as `LegalCase` and other utilities required for managing 
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
