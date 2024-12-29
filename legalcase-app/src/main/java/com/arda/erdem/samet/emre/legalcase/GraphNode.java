/**
 * @file GraphNode.java
 * @brief Represents a node in an adjacency list for a graph.
 *
 * @details
 * Each node corresponds to a case type and forms a part of a linked list structure
 * within the graph's adjacency list. It stores a case type identifier and a reference 
 * to the next node, enabling traversal and representation of graph connections.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note Used for building adjacency lists in graph data structures.
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
