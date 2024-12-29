/**
 * @file HuffmanNode.java
 * @brief Represents a node in the Huffman Tree.
 *
 * @details
 * Each node contains a character, its frequency, and references 
 * to left and right child nodes. This structure is used to build 
 * and traverse the Huffman Tree for encoding operations.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note A core component for constructing Huffman Trees.
 */

package com.arda.erdem.samet.emre.legalcase;

class HuffmanNode {
    char data;
    int frequency;
    HuffmanNode left, right;

    HuffmanNode(char data, int frequency) {
        this.data = data;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
}

