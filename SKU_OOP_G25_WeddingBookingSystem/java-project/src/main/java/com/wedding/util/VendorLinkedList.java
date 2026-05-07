package com.wedding.util;

import com.wedding.model.WeddingPackage;

// DATA STRUCTURE: Custom Singly Linked List to store wedding packages dynamically
// Used by: Lahiru (Package Management)
public class VendorLinkedList {

    // Inner Node class — each node holds one WeddingPackage
    private static class Node {
        WeddingPackage data;
        Node next;

        Node(WeddingPackage data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;  // pointer to first node
    private int size;

    public VendorLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // ADD to end of list
    public void add(WeddingPackage pkg) {
        Node newNode = new Node(pkg);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // REMOVE by package ID
    public boolean remove(String packageId) {
        if (head == null) return false;

        if (head.data.getId().equals(packageId)) {
            head = head.next;
            size--;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.getId().equals(packageId)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // FIND by package ID
    public WeddingPackage findById(String packageId) {
        Node current = head;
        while (current != null) {
            if (current.data.getId().equals(packageId)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    // GET ALL as array for display
    public WeddingPackage[] toArray() {
        WeddingPackage[] arr = new WeddingPackage[size];
        Node current = head;
        int i = 0;
        while (current != null) {
            arr[i++] = current.data;
            current = current.next;
        }
        return arr;
    }

    // BUBBLE SORT by price (ascending)
    // ALGORITHM: Bubble Sort — compares adjacent nodes, swaps if out of order
    public void sortByPrice() {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.data.getPrice() > current.next.data.getPrice()) {
                    // Swap the data (not the nodes)
                    WeddingPackage temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    // BUBBLE SORT by name alphabetically
    public void sortByName() {
        if (head == null || head.next == null) return;

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                if (current.data.getName().compareToIgnoreCase(current.next.data.getName()) > 0) {
                    WeddingPackage temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    public int getSize() { return size; }

    public boolean isEmpty() { return size == 0; }
}
