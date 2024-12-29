/**
 * @file CaseNode.java
 * @brief Implements a doubly linked list node for legal case management.
 *
 * @details
 * This class defines a node structure for a doubly linked list used in the 
 * Legal Case Tracker application. Each node contains a reference to a 
 * `LegalCase` object and pointers to the previous and next nodes, allowing 
 * efficient traversal in both directions.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note Part of the linked list implementation for case navigation.
 */

package com.arda.erdem.samet.emre.legalcase;


/**
 * Represents a doubly linked list node for managing legal cases.
 * Each node contains a reference to a `LegalCase` object, as well as references
 * to the previous and next nodes in the doubly linked list.
 *
 * @fields
 * - `data` (LegalCase): The `LegalCase` object stored in this node.
 * - `next` (CaseNode): A reference to the next node in the list, or `null` if this is the last node.
 * - `prev` (CaseNode): A reference to the previous node in the list, or `null` if this is the first node.
 *
 * @constructor
 * - `CaseNode(LegalCase data)`: Initializes a node with the given legal case.
 *   @param data The `LegalCase` object to store in this node.
 *
 */
public class CaseNode {
    LegalCase data;
    CaseNode next;
    CaseNode prev;

    /**
     * @brief Constructor for creating a CaseNode instance.
     * 
     * @details Initializes a CaseNode with the provided LegalCase data.
     * Sets the next and previous references to null, creating a standalone node.
     * 
     * @param data The LegalCase object to store in this node.
     */
    public CaseNode(LegalCase data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }}






