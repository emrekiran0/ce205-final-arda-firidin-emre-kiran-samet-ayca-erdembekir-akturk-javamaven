package com.arda.erdem.samet.emre.legalcase;

import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

class MinHeap {
    private PriorityQueue<HuffmanNode> heap;

    MinHeap(Map<Character, Integer> frequencyMap) {
        heap = new PriorityQueue<>(Comparator.comparingInt(node -> node.frequency));
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            heap.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
    }

    void insert(HuffmanNode node) {
        heap.add(node);
    }

    HuffmanNode extractMin() {
        return heap.poll();
    }

    int size() {
        return heap.size();
    }
}
