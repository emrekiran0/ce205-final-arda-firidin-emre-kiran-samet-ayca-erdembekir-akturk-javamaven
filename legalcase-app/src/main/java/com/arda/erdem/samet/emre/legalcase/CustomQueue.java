package com.arda.erdem.samet.emre.legalcase;

import static java.lang.System.out;


public class CustomQueue {
    int front, rear;
    int[] items;
    int maxSize;
    
    public CustomQueue(int size) {
        this.front = -1;
        this.rear = -1;
        this.maxSize = size;
        this.items = new int[size];
    }

    public boolean isEmpty() {
        return rear == -1;
    }

    public void enqueue(int value) {
        if (rear == maxSize - 1) {
            out.println("Queue is full. Cannot enqueue " + value);
            return;
        }
        if (front == -1) front = 0;
        items[++rear] = value;
    }

    public int dequeue() {
        if (isEmpty()) {
            out.println("Queue is empty. Cannot dequeue.");
            return -1;
        }
        int value = items[front++];
        if (front > rear) {
            front = rear = -1; // Kuyruk sıfırlanır
        }
        return value;
    }}

