/**
 * @brief Package containing the classes and logic for the Legal Case Management System.
 * 
 * @details This package, `com.arda.erdem.samet.emre.legalcase`, is the primary namespace
 * for all classes, data structures, and functionalities involved in the Legal Case Management System. 
 * It includes classes such as `LegalCase` and other utilities required for managing 
 */
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
 */
public class GraphNode {
    int caseType;
    GraphNode next;

    /**
     * @brief Constructor for creating a GraphNode.
     * 
     * @details Initializes a graph node with the specified case type and 
     * sets the next pointer to null.
     * 
     * @param caseType The type of the case represented by this node.
     */
    public GraphNode(int caseType) {
        this.caseType = caseType;
        this.next = null;
    }
}
