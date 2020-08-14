package com.github.supermoonie.cert;

import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;

/**
 * @author supermoonie
 * @since 2020/8/13
 */
public class CertificateTester {

    public static void main(String[] args) throws CertificateException, IOException, NoSuchAlgorithmException {
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        File cert = selfSignedCertificate.certificate();
        File privateKey = selfSignedCertificate.privateKey();

        FileUtils.copyFile(cert, new File("/Users/moonie/Desktop/self.crt"));
        FileUtils.copyFile(privateKey, new File("/Users/moonie/Desktop/self.key"));
        JcaPKCS8Generator gen1 = new JcaPKCS8Generator(selfSignedCertificate.key(), null);
        PemObject obj1 = gen1.generate();
        StringWriter sw1 = new StringWriter();
        try (JcaPEMWriter pw = new JcaPEMWriter(sw1)) {
            pw.writeObject(obj1);
        }
        String pkcs8Key1 = sw1.toString();
        FileOutputStream fos1 = new FileOutputStream("/Users/moonie/Desktop/self.pem");
        fos1.write(pkcs8Key1.getBytes());
        fos1.flush();
        fos1.close();
    }
}
