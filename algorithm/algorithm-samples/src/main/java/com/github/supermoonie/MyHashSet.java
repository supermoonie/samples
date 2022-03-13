package com.github.supermoonie;

import java.util.LinkedList;

/**
 * @author Administrator
 * @since 2022/3/5
 */
public class MyHashSet {

    private final int base = 1024;
    private final LinkedList<LinkedList<Integer>> data;

    public MyHashSet() {
        data = new LinkedList<>();
        for (int i = 0; i < base; i++) {
            data.offerLast(new LinkedList<>());
        }
    }

    public void add(int key) {
        int hash = hash(key);
        LinkedList<Integer> list = data.get(hash);
        for (Integer next : list) {
            if (next == key) {
                return;
            }
        }
        list.offerLast(key);
    }

    public void remove(int key) {
        int hash = hash(key);
        LinkedList<Integer> list = data.get(hash);
        for (Integer next : list) {
            if (next == key) {
                list.remove(next);
                return;
            }
        }
    }

    public boolean contains(int key) {
        int hash = hash(key);
        LinkedList<Integer> list = data.get(hash);
        for (Integer next : list) {
            if (next == key) {
                return true;
            }
        }
        return false;
    }

    private int hash(int key) {
        return key % base;
    }

    public static void main(String[] args) {
        MyHashSet set = new MyHashSet();
        set.add(1);
        set.add(2);
        System.out.println(set.contains(1));
        System.out.println(set.contains(2));
        System.out.println(set.contains(3));
        set.remove(1);
        System.out.println(set.contains(1));
        System.out.println(set.contains(2));
    }
}
