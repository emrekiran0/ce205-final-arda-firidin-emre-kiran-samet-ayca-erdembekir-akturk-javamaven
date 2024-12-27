package com.arda.erdem.samet.emre.legalcase;

/**
 * Represents a node in the Huffman Tree.
 * Each node contains a character, its frequency, and references to its left and right children.
 *
 * @fields
 * - `data` (char): The character represented by the node. For internal nodes, this is typically a placeholder (e.g., '$').
 * - `frequency` (int): The frequency of the character or the combined frequency of child nodes for internal nodes.
 * - `left` (HuffmanNode): Reference to the left child node (represents '0' in the Huffman Tree).
 * - `right` (HuffmanNode): Reference to the right child node (represents '1' in the Huffman Tree).
 *
 * @constructor
 * - `HuffmanNode(char data, int frequency)`: Initializes a node with the given character and frequency.
 *   @param data The character represented by the node.
 *   @param frequency The frequency of the character.
 *
 * @example
 * ```java
 * HuffmanNode node = new HuffmanNode('a', 5);
 * System.out.println("Character: " + node.data + ", Frequency: " + node.frequency);
 * ```
 */
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

