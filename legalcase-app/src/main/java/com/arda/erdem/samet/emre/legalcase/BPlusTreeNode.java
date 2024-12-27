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
 * @example
 * ```java
 * BPlusTreeNode leafNode = new BPlusTreeNode(true);
 * System.out.println("Leaf Node Created: " + leafNode.isLeaf);
 *
 * BPlusTreeNode internalNode = new BPlusTreeNode(false);
 * System.out.println("Internal Node Created: " + internalNode.isLeaf);
 * ```
 *
 * @note Leaf nodes store actual data (`LegalCase` objects) and are linked for efficient traversal.
 *       Internal nodes only store keys and child pointers for navigating the tree.
 */
class BPlusTreeNode {
    boolean isLeaf;               
    int numKeys;                  
    int[] keys;                   
    LegalCase[] cases;            
    BPlusTreeNode[] children;     
    BPlusTreeNode next;           

    public static final int MAX = 50; 

 
    public BPlusTreeNode(boolean isLeaf) {
        this.isLeaf = isLeaf;                    
        this.numKeys = 0;                        
        this.keys = new int[MAX];                
        this.cases = new LegalCase[MAX];         
        this.children = new BPlusTreeNode[MAX + 1]; 
        this.next = null;                        
    }
}
