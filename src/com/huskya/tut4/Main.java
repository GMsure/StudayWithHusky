package com.huskya.tut4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        List<String> data = Arrays.asList("apple", "banana");
        List<String> result = SimpleStream.<String>of(data)
                .filter(s -> s.startsWith("a"))
                .map(String::toUpperCase)
                .collect();
        System.out.println(result);

    }
}
