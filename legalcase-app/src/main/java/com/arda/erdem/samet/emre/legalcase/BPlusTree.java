/**
 * @file BPlusTree.java
 * @brief Implements a B+ Tree data structure.
 *
 * @details
 * The B+ Tree is optimized for managing sorted data and is widely used in database and file
 * systems. It supports efficient insertion, deletion, and range queries, with nodes organized
 * to minimize disk I/O.
 *
 * @field root Represents the root node of the B+ Tree, initially null.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note Designed to handle sorted data efficiently, with separate structures for internal
 * and leaf nodes.
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
