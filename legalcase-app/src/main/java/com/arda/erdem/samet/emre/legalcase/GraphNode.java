package com.arda.erdem.samet.emre.legalcase;


public class GraphNode {
    int caseType;
    GraphNode next;

    public GraphNode(int caseType) {
        this.caseType = caseType;
        this.next = null;
    }
}
