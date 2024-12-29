/**
 * @brief The primary package for the Legal Case Management System.
 *
 * @details This package, `com.arda.erdem.samet.emre.legalcase`, contains all the 
 * essential classes, data structures, and logic required for implementing 
 * the Legal Case Management System. It includes functionality for managing 
 * legal cases, scheduling, Huffman encoding, and other utility operations.
 */

package com.arda.erdem.samet.emre.legalcase;

/**
 * Represents a node in an XOR linked list for managing plaintiffs.
 * Each node contains a reference to a `LegalCase` object and an XOR link to the previous and next nodes.
 * This structure reduces memory usage by combining pointers into a single link.
 *
 * @fields
 * - `data` (LegalCase): The `LegalCase` object associated with this node, containing plaintiff information.
 * - `xorLink` (PlaintiffNode): An XOR-combined reference to the previous and next nodes in the linked list.
 *
 * @constructor
 * - `PlaintiffNode(LegalCase data)`: Initializes a node with the specified `LegalCase` object.
 *   @param data The `LegalCase` object containing plaintiff information to be stored in this node.
 *
 *
 * @note The XOR link allows for efficient doubly linked list traversal with reduced memory overhead.
 *       Use helper methods to calculate the XOR link during traversal.
 */
class PlaintiffNode {
    LegalCase data;
    PlaintiffNode xorLink;

    /**
     * @brief Constructor for creating a PlaintiffNode instance.
     *
     * @details Initializes a PlaintiffNode with a given LegalCase object and 
     * sets the XOR link to null. This node can be used in an XOR-linked list 
     * for efficient navigation of plaintiff-related data.
     *
     * @param data The LegalCase object associated with the plaintiff.
     */
    public PlaintiffNode(LegalCase data) {
        this.data = data;
        this.xorLink = null;
    }}
