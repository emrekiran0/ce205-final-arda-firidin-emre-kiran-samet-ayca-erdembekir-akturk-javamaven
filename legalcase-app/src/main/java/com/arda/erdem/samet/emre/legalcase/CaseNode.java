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
 * @example
 * ```java
 * LegalCase legalCase = new LegalCase(...); // Example LegalCase object
 * CaseNode node = new CaseNode(legalCase);
 * System.out.println("Case Node created with Case ID: " + node.data.caseID);
 * ```
 */
public class CaseNode {
    LegalCase data;
    CaseNode next;
    CaseNode prev;

    public CaseNode(LegalCase data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
    }






