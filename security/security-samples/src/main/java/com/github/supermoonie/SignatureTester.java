package com.github.supermoonie;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * @author supermoonie
 * @date 2020-06-03
 */
public class SignatureTester {

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] data = "supermoonie".getBytes(StandardCharsets.UTF_8);
        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(1024);
        KeyPair keyPair = kpg.generateKeyPair();

        signature.initSign(keyPair.getPrivate());
        signature.update(data);
        byte[] sign = signature.sign();

        signature.initVerify(keyPair.getPublic());
        signature.update(data);
        boolean status = signature.verify(sign);

        System.out.println(status);
    }
}
