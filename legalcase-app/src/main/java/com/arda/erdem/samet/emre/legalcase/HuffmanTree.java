package com.arda.erdem.samet.emre.legalcase;

import java.util.HashMap;
import java.util.Map;

class HuffmanTree {
    private Map<Character, String> huffmanCodes;
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

    private void generateCodes(HuffmanNode node, String code) {
        if (node == null) return;

        if (node.data != '$') {
            huffmanCodes.put(node.data, code);
        }

        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public Map<Character, String> getHuffmanCodes() {
        return huffmanCodes;
    }
}

