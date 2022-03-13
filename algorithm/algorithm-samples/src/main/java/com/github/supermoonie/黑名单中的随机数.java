package com.github.supermoonie;

import java.util.Random;

/**
 * @author Administrator
 * @since 2022/3/6
 */
public class 黑名单中的随机数 {

    private final int[] whiteList;
    private int len;
    private Random r;

    public 黑名单中的随机数(int n, int[] blacklist) {
        r = new Random();
        whiteList = new int[n];
        len = 0;
        for (int i = 0; i < n; i ++) {
            boolean exist = false;
            for (int b : blacklist) {
                if (b == i) {
                    exist = true;
                    break;
                }
            }
            if (exist) continue;
            whiteList[len] = i;
            len ++;
        }
    }

    public int pick() {
        return whiteList[r.nextInt(len)];
    }

    public static void main(String[] args) {
        黑名单中的随机数 h = new 黑名单中的随机数(7, new int[]{2, 3, 5});
        System.out.println(h.pick());
        System.out.println(h.pick());
        System.out.println(h.pick());
        System.out.println(h.pick());
        System.out.println(h.pick());
        System.out.println(h.pick());
    }
}
