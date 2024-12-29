/**
 * @brief Package containing the classes and logic for the Legal Case Management System.
 * 
 * @details This package, `com.arda.erdem.samet.emre.legalcase`, is the primary namespace
 * for all classes, data structures, and functionalities involved in the Legal Case Management System. 
 * It includes classes such as `LegalCase`, `BPlusTree`, and other utilities required for managing 
 * legal cases, scheduling, and data operations.
 */
package com.arda.erdem.samet.emre.legalcase;

/**
 * @brief Represents a B+ Tree data structure.
 * 
 * @details The B+ Tree is designed for efficiently managing sorted data, commonly used in
 * database systems and file systems for quick access and insertion. This class initializes
 * an empty tree with a null root.
 * 
 * @field root The root node of the B+ Tree. Initially set to null.
 * 
 * @constructor
 * Initializes a new instance of the BPlusTree class with no root node.
 */
class BPlusTree {
    BPlusTreeNode root;

    /**
     * @brief Initializes an empty B+ Tree.
     * 
     * @details Sets the root node to null, preparing the structure for further operations.
     */
    public BPlusTree() {
        this.root = null;
    }}
