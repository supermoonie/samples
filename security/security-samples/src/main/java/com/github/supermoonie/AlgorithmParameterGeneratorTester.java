package com.github.supermoonie;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * @author supermoonie
 * @date 2020-06-02
 */
public class AlgorithmParameterGeneratorTester {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        Security.addProvider(new BouncyCastleProvider());
        AlgorithmParameterGenerator algorithmParameterGenerator =
                AlgorithmParameterGenerator.getInstance("DES", new BouncyCastleProvider());
        algorithmParameterGenerator.init(1024);
        AlgorithmParameters ap = algorithmParameterGenerator.generateParameters();
        System.out.println(new BigInteger(ap.getEncoded()).toString());
    }
}
