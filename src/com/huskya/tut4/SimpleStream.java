package com.huskya.tut4;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SimpleStream<T> {
    private final List<T> source;

    private SimpleStream(List<T> source) {
        this.source = source;
    }

    public static <T> SimpleStream<T> of(List<T> source) {
        return new SimpleStream<T>(source);
    }

    public SimpleStream<T> filter(Predicate<? super T> predicate) {
        List<T> filtered = new ArrayList<>();
        for(T t : source) {
            if(predicate.test(t)) {
                filtered.add(t);
            }
        }
        return new SimpleStream<>(filtered);
    }

    public <R> SimpleStream<R> map(Function<? super T, ? extends R> mapper) {
        List<R> mapped = new ArrayList<>();
        for (T t : source) {
            mapped.add(mapper.apply(t));
        }

        return new SimpleStream<>(mapped);
    }

    public List<T> collect() {
        return new ArrayList<>(source);
    }

    public void forEach(Consumer<? super T> action) {
        for(T t : source) {
            action.accept(t);
        }
    }
}
