package com.github.supermoonie;

import org.junit.Test;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * @author supermoonie
 * @date 2020-06-01
 */
public class HcAppTest {

    @Test
    public void test() throws Exception{
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = (X509Certificate) cf.generateCertificate(new FileInputStream("D:\\cert\\cert.crt"));
        PublicKey publicKey = cert.getPublicKey();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String publicKeyString = base64Encoder.encode(publicKey.getEncoded());
        System.out.println("-----------------公钥--------------------");
        System.out.println(publicKeyString);
        System.out.println("-----------------公钥--------------------");

        try
        {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream fileInputStream = new FileInputStream("D:\\cert\\cert.cert");
            char[] nPassword = null;
            keyStore.load(fileInputStream, null);
            fileInputStream.close();
            System.out.println("keystore type=" + keyStore.getType());

            Enumeration<String> enumeration = keyStore.aliases();

            if (enumeration.hasMoreElements())
            {
                String keyAlias =  enumeration.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
            }
            System.out.println("is key entry=" + keyStore.isKeyEntry(""));
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("", nPassword);
            Certificate certificate = keyStore.getCertificate("");
            PublicKey certPublicKey = certificate.getPublicKey();
            System.out.println("cert class = " + certificate.getClass().getName());
            System.out.println("cert = " + certificate);
            System.out.println("public key = " + certPublicKey);
            System.out.println("private key = " + privateKey);

        } catch (Exception e)
        {
            System.out.println(e);
        }
    }

}