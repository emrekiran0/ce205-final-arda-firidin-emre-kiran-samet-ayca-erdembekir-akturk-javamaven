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
 * @example
 * ```java
 * LegalCase legalCase = new LegalCase(...); // Example LegalCase object
 * PlaintiffNode node = new PlaintiffNode(legalCase);
 * System.out.println("Plaintiff Node Created with Case ID: " + node.data.caseID);
 * ```
 *
 * @note The XOR link allows for efficient doubly linked list traversal with reduced memory overhead.
 *       Use helper methods to calculate the XOR link during traversal.
 */
class PlaintiffNode {
    LegalCase data;
    PlaintiffNode xorLink;

    public PlaintiffNode(LegalCase data) {
        this.data = data;
        this.xorLink = null;
    }}
