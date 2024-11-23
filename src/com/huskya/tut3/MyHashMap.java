package com.huskya.tut3;

// 数组 + 链表 + 红黑树
// `HashMap` 的核心数据结构是一个数组 `Node<K,V>[]`
// 当发生哈希冲突时，使用链表存储冲突节点
// 在链表长度超过阈值（默认8）时，链表会转换为红黑树 以提高查找效率
// [Array] -> [Node1] -> [Node2] -> [Node3]

import java.util.HashMap;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node[] table;
    private int size;
    private int capacity;
    private int threshold;

    public static class Node<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialCapacity, float loadFactor) {
        if(initialCapacity <= 0 || loadFactor <= 0) {
            throw new IllegalArgumentException("Invalid initial capacity or load factor");
        }

        this.capacity = initialCapacity;
        this.threshold = (int) (initialCapacity * loadFactor);
        this.table = new Node[initialCapacity];
    }

    private int hash(Object key) {
        int h = (key == null) ? 0 : key.hashCode();
        return h ^ (h >>> 16);
    }

    public void put(K key, V value) {
        int hash = hash(key);
        int index = hash & (capacity - 1);

        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.hash == hash && (node.key == key || (key != null && key.equals(node.key)))) {
                // exist
                node.value = value;
                return;
            }
        }

        Node<K, V> newNode = new Node<>(hash, key, value, table[index]);
        table[index] = newNode;
        size ++;

        if (size > threshold) {
            resize();
        }
    }

    public V get(Object key) {
        int hash = hash(key);
        int index = hash & (capacity - 1);

        for (Node<K, V> node = table[index]; node != null; node = node.next) {
            if (node.hash == hash && (node.key == key || (key != null && key.equals(node.key)))) {
                return node.value;
            }
        }
        return null;
    }

    public V remove(Object key) {
        int hash = hash(key);
        int index = hash & (capacity - 1);

        Node<K, V> prev = null;
        Node<K, V> current = table[index];

        while (current != null) {
            if(current.hash == hash && (current.key == key || (key != null && key.equals(current.key)))) {
                if (prev == null) {
                    table[index] = current.next; // 删除的是头节点
                } else {
                    prev.next = current.next; // 非头节点
                }

                size--;
                return current.value;
            }

            prev = current;
            current = current.next;
        }

        return null;
    }

    public int getCapacity() {
        return capacity;
    }

    public Node<K, V> getBucketHead(int index) {
        if (index < 0 || index >= table.length) {
            throw new IllegalArgumentException("Invalid bucket index");
        }
        return table[index];
    }

    private void resize() {
        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = new Node[newCapacity];
        threshold = (int) (newCapacity * DEFAULT_LOAD_FACTOR);

        for(Node<K, V> oldNode : table) {
            while(oldNode != null) {
                Node<K, V> next = oldNode.next;
                int index = oldNode.hash & (newCapacity - 1);
                oldNode.next = newTable[index];
                newTable[index] = oldNode;
                oldNode = next;
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    public int size() {
        return size;
    }
}

