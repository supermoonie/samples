package com.github.supermoonie;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

/**
 * @author supermoonie
 * @date 2020-06-02
 */
public class MessageDigestTester {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] bytes = "supermoonie".getBytes(StandardCharsets.UTF_8);
        System.out.println(digest(bytes, "md2"));
        System.out.println(digest(bytes, "md5"));
        System.out.println(digest(bytes, "sha-1"));
        System.out.println(digest(bytes, "sha"));
        System.out.println(digest(bytes, "sha-256"));
        System.out.println(digest(bytes, "sha-384"));
        System.out.println(digest(bytes, "sha-512"));
    }

    private static String digest(byte[] bytes, String algorithm) throws NoSuchAlgorithmException {
        Base64.Encoder encoder = Base64.getEncoder();
        MessageDigest md5 = MessageDigest.getInstance(algorithm);
        md5.update("supermoonie".getBytes());
        System.out.println("provider: " + md5.getProvider());
        return encoder.encodeToString(md5.digest());
    }
}
