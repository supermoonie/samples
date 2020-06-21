package com.github.supermoonie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author supermoonie
 * @date 2020-06-02
 */
public class DigestInputStreamTester {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        byte[] input = "supermoonie".getBytes(StandardCharsets.UTF_8);
        MessageDigest md5 = MessageDigest.getInstance("md5");
        DigestInputStream dis = new DigestInputStream(new ByteArrayInputStream(input), md5);
        dis.read(input, 0, input.length);
        byte[] output = dis.getMessageDigest().digest();
        dis.close();
        System.out.println(Base64.getEncoder().encodeToString(output));
    }
}
