package com.huskya.tut3;

public class Main {
    public static void main(String[] args) {
        MyHashMap<String, Integer> map = new MyHashMap<String, Integer>();

        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        System.out.println(map.get("two1"));

        for (int i = 0; i < 2000; i++) {
            map.put("key"+i, i);
        }

        System.out.println(map.get("key100"));

    }
}
