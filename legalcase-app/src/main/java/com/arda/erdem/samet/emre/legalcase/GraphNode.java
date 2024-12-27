package com.arda.erdem.samet.emre.legalcase;

/**
 * Represents a node in an adjacency list for a graph.
 * Each node contains a case type identifier and a reference to the next node
 * in the adjacency list, forming a linked list structure.
 *
 * @fields
 * - `caseType` (int): The identifier of the case type represented by this node.
 * - `next` (GraphNode): A reference to the next node in the adjacency list, or `null` if this is the last node.
 *
 * @constructor
 * - `GraphNode(int caseType)`: Initializes a node with the specified case type.
 *   @param caseType The identifier of the case type.
 *
 * @example
 * ```java
 * GraphNode node = new GraphNode(1);
 * System.out.println("Node created with case type: " + node.caseType);
 * ```
 */
public class GraphNode {
    int caseType;
    GraphNode next;

    public GraphNode(int caseType) {
        this.caseType = caseType;
        this.next = null;
    }
}
