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

