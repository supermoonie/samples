package com.github.supermoonie;

/**
 * @author Administrator
 * @since 2022/3/5
 */
public class MyLinkedList {

    private static class Node {
        private int value;
        private Node next;
    }

    private Node head;
    private int length;

    public MyLinkedList() {
        length = 0;
    }

    public int get(int index) {
        if (null == head) {
            return -1;
        }
        Node cur = head;
        for (int i = 0; i < index; i++) {
            if (null == cur.next) {
                return -1;
            }
            cur = cur.next;
        }
        return cur.value;
    }

    public void addAtHead(int val) {
        Node node = new Node();
        node.value = val;
        if (null != head) {
            node.next = head;
        }
        head = node;
        length++;
    }

    public void addAtTail(int val) {
        Node node = new Node();
        node.value = val;
        if (null == head) {
            head = node;
            length++;
            return;
        }
        Node cur = head;
        while (null != cur.next) {
            cur = cur.next;
        }
        cur.next = node;
        length++;
    }

    public void addAtIndex(int index, int val) {
        if (index > length) {
            return;
        }
        if (index == length) {
            addAtTail(val);
            return;
        }
        if (index <= 0) {
            addAtHead(val);
            return;
        }
        Node node = new Node();
        node.value = val;
        Node prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        Node cur = prev.next;
        prev.next = node;
        node.next = cur;
    }

    public void deleteAtIndex(int index) {
        if (index > length -1 || index < 0 || length == 0) {
            return;
        }
        if (0 == index) {
            if (null != head.next) {
                Node next = head.next;
                head.next = null;
                head = next;
            } else {
                head = null;
            }
            return;
        }
        Node prev = head;
        for (int i = 1; i < index - 1; i++) {
            prev = prev.next;
        }
        Node cur = prev.next;
        prev.next = cur.next;
        cur.next = null;
        length --;
    }

    public static void main(String[] args) {
        MyLinkedList linkedList = new MyLinkedList();
//        linkedList.addAtHead(1);
//        linkedList.addAtTail(3);
//        linkedList.addAtIndex(1,2);   //链表变为1-> 2-> 3
//        System.out.println(linkedList.get(1));            //返回2
//        linkedList.deleteAtIndex(1);  //现在链表是1-> 3
//        System.out.println(linkedList.get(1));            //返回3
        linkedList.addAtIndex(0, 10);
        linkedList.addAtIndex(0, 20);
        linkedList.addAtIndex(1, 30);
        System.out.println(linkedList.get(0));
    }
}
