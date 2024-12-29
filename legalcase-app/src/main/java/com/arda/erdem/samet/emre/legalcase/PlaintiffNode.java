/**
 * @file PlaintiffNode.java
 * @brief Represents a node in an XOR linked list for managing plaintiffs.
 *
 * @details
 * This node stores a reference to a `LegalCase` object and uses an XOR-combined link
 * to connect to the previous and next nodes. The XOR linked list reduces memory
 * usage while maintaining doubly linked list functionality.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note The XOR link simplifies memory usage but requires careful management 
 * during traversal. Use helper methods to compute and utilize XOR links correctly.
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
