package com.arda.erdem.samet.emre.legalcase;

public class Graph {
    int numVertices;           // Düğüm sayısı
    GraphNode[] adjLists;      // Bağlı liste
    boolean[] visited;         // Ziyaret kontrolü

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
