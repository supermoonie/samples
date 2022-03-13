package com.github.supermoonie;

import java.util.LinkedList;

/**
 * @author Administrator
 * @since 2022/3/5
 */
public class MyHashMap {

    private static class Entry {

        private final int key;
        private final int value;

        private Entry(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final int base = 1024;
    private final LinkedList<LinkedList<Entry>> data;

    public MyHashMap() {
        data = new LinkedList<>();
        for (int i = 0; i < base; i ++) {
            data.offerLast(new LinkedList<>());
        }
    }

    public void put(int key, int value) {
        int hash = hash(key);
        LinkedList<Entry> list = data.get(hash);
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).key == key) {
                list.set(i, new Entry(key, value));
                return;
            }
        }
        list.offerLast(new Entry(key, value));
    }

    public int get(int key) {
        int hash = hash(key);
        LinkedList<Entry> list = data.get(hash);
        for (Entry entry : list) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return -1;
    }

    public void remove(int key) {
        int hash = hash(key);
        LinkedList<Entry> list = data.get(hash);
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).key == key) {
                list.remove(i);
                return;
            }
        }
    }

    private int hash(int key) {
        return key % base;
    }

    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();
        map.put(1, 1);
        map.put(2, 2);
        System.out.println(map.get(1));
        System.out.println(map.get(3));
        map.put(2, 1);
        System.out.println(map.get(2));
        map.remove(2);
        System.out.println(map.get(2));
    }
}
