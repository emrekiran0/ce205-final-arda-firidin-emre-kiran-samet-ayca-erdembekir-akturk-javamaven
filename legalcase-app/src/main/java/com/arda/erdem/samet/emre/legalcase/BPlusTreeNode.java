/**
 * @file BPlusTreeNode.java
 * @brief Represents a node in a B+ Tree data structure.
 *
 * @details
 * This class serves as the fundamental building block for B+ Trees, accommodating both internal
 * and leaf nodes. It stores keys, data (`LegalCase` objects), and child pointers. Leaf nodes
 * are linked for efficient sequential traversal, while internal nodes manage child relationships.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note
 * - Leaf nodes store actual data (`LegalCase` objects) and support linked traversal.
 * - Internal nodes store keys and child pointers for navigation within the tree.
 */

package com.arda.erdem.samet.emre.legalcase;

/**
 * Represents a node in a B+ Tree.
 * A node can either be an internal node or a leaf node, depending on the `isLeaf` flag.
 * This class stores keys, associated data (`LegalCase` objects), and child pointers for internal nodes.
 * It also supports linking leaf nodes for efficient traversal.
 *
 * @fields
 * - `isLeaf` (boolean): Indicates whether the node is a leaf node.
 * - `numKeys` (int): The current number of keys in the node.
 * - `keys` (int[]): An array of keys stored in the node.
 * - `cases` (LegalCase[]): An array of `LegalCase` objects associated with the keys (used in leaf nodes).
 * - `children` (BPlusTreeNode[]): An array of child pointers for internal nodes.
 * - `next` (BPlusTreeNode): A pointer to the next leaf node, used for linked leaf traversal.
 * - `MAX` (int, static): The maximum number of keys a node can store before splitting.
 *
 * @constructor
 * - `BPlusTreeNode(boolean isLeaf)`: Initializes a new B+ Tree node as either a leaf or internal node.
 *   @param isLeaf A boolean value indicating if the node is a leaf.
 *
 *
 * @note Leaf nodes store actual data (`LegalCase` objects) and are linked for efficient traversal.
 *       Internal nodes only store keys and child pointers for navigating the tree.
 */
class BPlusTreeNode {
    boolean isLeaf;               // Yaprak düğüm olup olmadığını belirtir
    int numKeys;                  // Mevcut anahtar sayısı
    int[] keys;                   // Anahtarları tutan dizi
    LegalCase[] cases;            // LegalCase nesnelerini tutar
    BPlusTreeNode[] children;     // Çocuk düğümleri tutan dizi (iç düğümler için)
    BPlusTreeNode next;           // Yaprak düğümler arasında bağlantıyı sağlayan işaretçi

    
    /**
     * @brief Maximum allowed size for specific data structures.
     * 
     * @details Represents the maximum number of elements or entries 
     * that certain arrays or collections can hold. Used in defining 
     * constraints within data structures like the B+ Tree.
     */
    public static final int MAX = 50; 

    // (Constructor):
    /**
     * @brief Constructor for creating a BPlusTreeNode instance.
     * 
     * @details Initializes a BPlusTreeNode, setting whether it's a leaf node
     * and initializing its arrays for keys, cases, and child nodes. Prepares
     * the structure for storing or referencing data in the B+ Tree.
     * 
     * @param isLeaf Indicates if the node is a leaf node.
     */
    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;                    // Yaprak düğüm olup olmadığını ayarlar
        this.numKeys = 0;                        // Başlangıçta anahtar sayısını sıfır yapar
        this.keys = new int[MAX];                // Anahtarları saklamak için bir dizi oluşturur
        this.cases = new LegalCase[MAX];         // LegalCase nesnelerini saklamak için bir dizi oluşturur
        this.children = new BPlusTreeNode[MAX + 1]; // Çocuk düğümler için dizi
        this.next = null;                        // Yaprak düğümler arasında bağlantı yok
    }
}
