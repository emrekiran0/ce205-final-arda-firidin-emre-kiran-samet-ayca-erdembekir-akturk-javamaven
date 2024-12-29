/**
 * @file MinHeap.java
 * @brief Manages Huffman nodes with a priority queue.
 *
 * @details
 * This class provides efficient management of Huffman nodes, 
 * ensuring the smallest frequency node is accessible for 
 * tree construction.
 *
 * @package com.arda.erdem.samet.emre.legalcase
 *
 * @note A key utility for building Huffman Trees.
 */

package com.arda.erdem.samet.emre.legalcase;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Represents a MinHeap data structure for managing Huffman nodes.
 * This class uses a priority queue to efficiently manage nodes based on their frequencies,
 * ensuring the smallest frequency node is always accessible.
 *
 * @fields
 * - `heap` (PriorityQueue<HuffmanNode>): A priority queue containing `HuffmanNode` objects,
 *   sorted by their frequency in ascending order.
 *
 * @constructor
 * - `MinHeap(Map<Character, Integer> frequencyMap)`: Initializes the MinHeap with nodes created
 *   from the character-frequency map.
 *   @param frequencyMap A map containing characters as keys and their frequencies as values.
 *
 * @methods
 * - `insert(HuffmanNode node)`: Inserts a new node into the MinHeap.
 *   @param node The `HuffmanNode` to be inserted.
 * - `extractMin()`: Removes and returns the node with the smallest frequency from the MinHeap.
 *   @return The `HuffmanNode` with the smallest frequency.
 * - `size()`: Returns the current size of the MinHeap.
 *   @return The number of nodes in the MinHeap.
 *
 */
class MinHeap {
	/**
	 * @brief A priority queue to manage Huffman nodes.
	 *
	 * @details The `heap` is used for building the Huffman Tree. It organizes 
	 * Huffman nodes based on their frequency, ensuring that the node with the 
	 * smallest frequency is always at the top for efficient tree construction.
	 */
    private PriorityQueue<HuffmanNode> heap;

    MinHeap(Map<Character, Integer> frequencyMap) {
        heap = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            heap.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
    }

    /**
     * Inserts a new node into the MinHeap.
     * This method adds the node to the priority queue, maintaining the heap property.
     *
     * @param node The `HuffmanNode` to be inserted into the MinHeap.
     */
    void insert(HuffmanNode node) {
        heap.add(node);
    }

    /**
     * Removes and returns the node with the smallest frequency from the MinHeap.
     * This method retrieves and removes the root node of the heap.
     *
     * @return The `HuffmanNode` with the smallest frequency.
     *
     * @note If the heap is empty, this method returns `null`.
     */
    HuffmanNode extractMin() {
        return heap.poll();
    }

    /**
     * Returns the current size of the MinHeap.
     * This method provides the number of nodes currently stored in the heap.
     *
     * @return The number of nodes in the MinHeap.
     */
    int size() {
        return heap.size();
    }
}
