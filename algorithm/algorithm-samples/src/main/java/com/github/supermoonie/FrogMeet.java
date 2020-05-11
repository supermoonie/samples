package com.github.supermoonie;

/**
 * 两青蛙相会问题：
 * 两只青蛙A和B在一个周长为L米的环上，A处于x点，B处于y点
 * A和B同时朝一个方向跳，A每次跳m米，B每次跳n米，AB没跳一次花费的时间一致
 * 问两只青蛙跳多少次才会碰面
 *
 * @author supermoonie
 * @since 2020/4/27
 */
public class FrogMeet {

    public static void main(String[] args) {
//        int l = 2000000;
//        long x = 0;
//        long y = 10;
//        int m = 2;
//        int n = 3;
//        long step = jump(l, x, y, m, n);
//        System.out.println(step);

        System.out.println(gcd(9, 6));
        LongReference x = new LongReference();
        LongReference y = new LongReference();

        System.out.println(exGcd(9, 6, x, y));
        System.out.println(y.value);
        System.out.println(x.value);
    }

    private static long exGcd(long a, long b, LongReference x, LongReference y) {
        if (b == 0) {
            x.value = 1L;
            y.value = 0L;
            return a;
        }
        long result = exGcd(b, a%b, x, y);
        System.out.println("x: " + x.value + ", y: " + y.value + ", a: " + a + ", b: " + b);
        long temp = x.value;
        x.value = y.value;
        y.value = temp - a/b*y.value;
        return result;
    }

    /**
     * 辗转相除法（欧几里得算法）求最大公约数
     */
    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * 求最小公倍数
     */
    private static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private static void swap(LongReference a, LongReference b) {
        long temp = a.value;
        a.value = b.value;
        b.value = temp;
    }

    private static long jump(LongReference x, long y, int m, int n, long mod) {
        if (m < n) {

        }
        return 0;
    }

    static class LongReference {
        long value;
    }
}
