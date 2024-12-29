/**
 * @brief Package containing the classes and logic for the Legal Case Management System.
 * 
 * @details This package, `com.arda.erdem.samet.emre.legalcase`, is the primary namespace
 * for all classes, data structures, and functionalities involved in the Legal Case Management System. 
 * It includes classes such as `LegalCase` and other utilities required for managing 
 */
package com.arda.erdem.samet.emre.legalcase;

import static java.lang.System.out;

/**
 * Represents a custom queue implementation for integer values.
 * This class provides basic queue operations such as enqueue, dequeue, and checking if the queue is empty.
 *
 * @fields
 * - `front` (int): The index of the front element in the queue.
 * - `rear` (int): The index of the last element in the queue.
 * - `items` (int[]): An array to store the queue elements.
 * - `maxSize` (int): The maximum size of the queue.
 *
 * @constructor
 * - `CustomQueue(int size)`: Initializes the queue with the specified maximum size.
 *   @param size The maximum number of elements the queue can hold.
 *
 * @methods
 * - `isEmpty()`: Checks if the queue is empty.
 *   @return `true` if the queue is empty, `false` otherwise.
 * - `enqueue(int value)`: Adds an element to the rear of the queue.
 *   @param value The value to be added to the queue.
 *   @note Prints an error message if the queue is full.
 * - `dequeue()`: Removes and returns the front element of the queue.
 *   @return The front element of the queue, or `-1` if the queue is empty.
 *   @note Prints an error message if the queue is empty.
 *
 */
public class CustomQueue {
    int front, rear;
    int[] items;
    int maxSize;
    
    /**
     * @brief Constructor for initializing a CustomQueue.
     * 
     * @details Creates a queue with a specified maximum size, initializing the 
     * front and rear pointers to -1 and allocating an array to store the queue elements.
     * 
     * @param size The maximum number of elements the queue can hold.
     */
    public CustomQueue(int size) {
        this.front = -1;
        this.rear = -1;
        this.maxSize = size;
        this.items = new int[size];
    }
    /**
     * Checks if the queue is empty.
     *
     * @return `true` if the queue is empty, `false` otherwise.
     *
     */
    public boolean isEmpty() {
        return rear == -1;
    }

    /**
     * Adds an element to the rear of the queue.
     *
     * @param value The value to be added to the queue.
     * @note Prints an error message if the queue is full.
     *
     */
    public void enqueue(int value) {
        if (rear == maxSize - 1) {
            out.println("Queue is full. Cannot enqueue " + value);
            return;
        }
        if (front == -1) front = 0;
        items[++rear] = value;
    }

    /**
     * Removes and returns the front element of the queue.
     *
     * @return The front element of the queue, or `-1` if the queue is empty.
     * @note Prints an error message if the queue is empty.
     *
     */
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

