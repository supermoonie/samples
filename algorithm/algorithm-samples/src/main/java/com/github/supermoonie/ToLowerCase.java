package com.github.supermoonie;

/**
 * @author Administrator
 * @since 2022/3/5
 */
public class ToLowerCase {

    public static String toLowerCase(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i ++) {
            char c = s.charAt(i);
            if (c >= 65 && c <= 90) {
                c |= 32;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(toLowerCase("Hello"));
    }
}
