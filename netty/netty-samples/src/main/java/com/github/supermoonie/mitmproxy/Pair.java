package com.github.supermoonie.mitmproxy;

/**
 * @author supermoonie
 * @since 2020/8/16
 */
public class Pair<K, V> {

    private final K key;

    private final K value;

    public Pair(K key, K value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public K getValue() {
        return value;
    }
}
