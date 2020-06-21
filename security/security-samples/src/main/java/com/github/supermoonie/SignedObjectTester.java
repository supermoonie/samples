package com.github.supermoonie;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * @author supermoonie
 * @date 2020-06-03
 */
public class SignedObjectTester {

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException {
        Security.addProvider(new BouncyCastleProvider());
        byte[] data = "supermoonie".getBytes(StandardCharsets.UTF_8);
        Signature signature = Signature.getInstance("SHA256withRSA", "BC");
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "BC");
        kpg.initialize(1024);
        KeyPair keyPair = kpg.generateKeyPair();

        SignedObject signedObject = new SignedObject(data, keyPair.getPrivate(), signature);
        byte[] sign = signedObject.getSignature();

        boolean status = signedObject.verify(keyPair.getPublic(), signature);

        System.out.println(status);
    }
}
