package com.huskya.tut3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class HashMapPerformanceTest {


    public static void main(String[] args) {
        final int DATA_SIZE = 10_000_000; // 数据量
        final int TEST_QUERIES = 1_000_000; // 测试查找和删除次数
        Random random = new Random();

        // 随机生成数据
        int[] keys = new int[DATA_SIZE];
        int[] values = new int[DATA_SIZE];
        for (int i = 0; i < DATA_SIZE; i++) {
            keys[i] = random.nextInt(DATA_SIZE * 10); // 生成随机键
            values[i] = random.nextInt(DATA_SIZE * 10); // 生成随机值
        }

        // 测试原生 HashMap
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        long hashMapPutTime = measurePutPerformance(hashMap, keys, values);
        long hashMapGetTime = measureGetPerformance(hashMap, keys, TEST_QUERIES, random);
        long hashMapRemoveTime = measureRemovePerformance(hashMap, keys, TEST_QUERIES, random);

        // 测试 MyHashMap
        MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
        long myHashMapPutTime = measurePutPerformance(myHashMap, keys, values);
        long myHashMapGetTime = measureGetPerformance(myHashMap, keys, TEST_QUERIES, random);
        long myHashMapRemoveTime = measureRemovePerformance(myHashMap, keys, TEST_QUERIES, random);

        // 打印性能比较结果
        System.out.println("Performance Comparison (Time in ms):");
        System.out.println("Operation       | HashMap   | MyHashMap");
        System.out.printf("Put             | %6d ms   | %6d ms\n", hashMapPutTime, myHashMapPutTime);
        System.out.printf("Get             | %6d ms   | %6d ms\n", hashMapGetTime, myHashMapGetTime);
        System.out.printf("Remove          | %6d ms   | %6d ms\n", hashMapRemoveTime, myHashMapRemoveTime);

        // 分析 MyHashMap 的哈希桶分布
//        analyzeBucketDistribution(myHashMap, myHashMap.getCapacity());
    }

    // 测试插入性能
    private static long measurePutPerformance(HashMap<Integer, Integer> map, int[] keys, int[] values) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return System.currentTimeMillis() - start;
    }

    private static long measurePutPerformance(MyHashMap<Integer, Integer> map, int[] keys, int[] values) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], values[i]);
        }
        return System.currentTimeMillis() - start;
    }

    // 测试查找性能
    private static long measureGetPerformance(HashMap<Integer, Integer> map, int[] keys, int queries, Random random) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < queries; i++) {
            map.get(keys[random.nextInt(keys.length)]);
        }
        return System.currentTimeMillis() - start;
    }

    private static long measureGetPerformance(MyHashMap<Integer, Integer> map, int[] keys, int queries, Random random) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < queries; i++) {
            map.get(keys[random.nextInt(keys.length)]);
        }
        return System.currentTimeMillis() - start;
    }

    // 测试删除性能
    private static long measureRemovePerformance(HashMap<Integer, Integer> map, int[] keys, int queries, Random random) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < queries; i++) {
            map.remove(keys[random.nextInt(keys.length)]);
        }
        return System.currentTimeMillis() - start;
    }

    private static long measureRemovePerformance(MyHashMap<Integer, Integer> map, int[] keys, int queries, Random random) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < queries; i++) {
            map.remove(keys[random.nextInt(keys.length)]);
        }
        return System.currentTimeMillis() - start;
    }

    // 分析 MyHashMap 的桶分布
    private static void analyzeBucketDistribution(MyHashMap<Integer, Integer> map, int capacity) {
        int[] buckets = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            MyHashMap.Node<Integer, Integer> node = map.getBucketHead(i);
            int count = 0;
            while (node != null) {
                count++;
                node = node.next;
            }
            buckets[i] = count;
        }
        System.out.println("MyHashMap bucket distribution: " + Arrays.toString(buckets));
    }
}
