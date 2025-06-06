/**
 * @file HuffmanTree.java
 * @brief Implements the Huffman Tree for character encoding.
 *
 * @details
 * This class constructs a Huffman Tree using character frequencies 
 * and generates binary codes for efficient encoding. It relies on 
 * a MinHeap to build the tree and stores generated codes in a map 
 * for quick access.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note Designed for internal use in Huffman encoding operations.
 */

package com.arda.erdem.samet.emre.legalcase;

import java.util.HashMap;
import java.util.Map;


class HuffmanTree {
	
	/**
	 * @brief Stores the Huffman codes for characters and the root of the Huffman tree.
	 *
	 * @details These private fields are integral to the Huffman coding algorithm. 
	 * The `huffmanCodes` map associates each character with its corresponding Huffman code,
	 * while the `root` represents the root node of the Huffman tree.
	 *
	 * @field `huffmanCodes` (Map<Character, String>): A mapping of characters to their Huffman-encoded binary strings.
	 * @field `root` (HuffmanNode): The root node of the Huffman tree, which serves as the entry point for encoding and decoding operations.
	 */
    private Map<Character, String> huffmanCodes;
    
    /**
     * @brief Root node of the Huffman Tree.
     *
     * @details The `root` represents the top node in the Huffman Tree structure.
     * It is used to store the hierarchy of characters and their frequencies
     * for encoding and decoding operations.
     *
     * @note The `root` is initialized as `null` when the Huffman Tree is created
     * and is populated after constructing the tree based on character frequencies.
     */
    private HuffmanNode root;

    HuffmanTree(Map<Character, Integer> frequencyMap) {
        MinHeap minHeap = new MinHeap(frequencyMap);

        while (minHeap.size() > 1) {
            HuffmanNode left = minHeap.extractMin();
            HuffmanNode right = minHeap.extractMin();

            HuffmanNode newNode = new HuffmanNode('$', left.frequency + right.frequency);
            newNode.left = left;
            newNode.right = right;

            minHeap.insert(newNode);
        }

        root = minHeap.extractMin();
        huffmanCodes = new HashMap<>();
        generateCodes(root, "");
    }

    /**
     * Recursively generates Huffman codes for all characters in the Huffman Tree.
     * This method traverses the tree and assigns binary codes based on the path taken:
     * '0' for left branches and '1' for right branches.
     *
     * @param node The current `HuffmanNode` being processed.
     * @param code The binary code accumulated so far.
     *
     * @note Leaf nodes represent characters, and their codes are added to the `huffmanCodes` map.
     */
    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;

        if (node.data != '$') {
            huffmanCodes.put(node.data, code);
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    /**
     * Retrieves the Huffman codes generated by the Huffman Tree.
     *
     * @return A map of characters to their corresponding binary Huffman codes.
     *
     */
    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }
}

