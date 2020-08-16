package com.github.supermoonie.cert;

import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author supermoonie
 * @since 2020/8/13
 */
public class CertificateTester {

    public static void main(String[] args) throws Exception {
        SelfSignedCertificate selfSignedCertificate = new SelfSignedCertificate();
        File cert = selfSignedCertificate.certificate();
        File privateKey = selfSignedCertificate.privateKey();
        PrivateKey key = selfSignedCertificate.key();
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
        FileUtils.copyFile(cert, new File("/Users/moonie/Desktop/self.crt"));
        FileUtils.copyFile(privateKey, new File("/Users/moonie/Desktop/self.key"));

        byte[] keyBytes = Files.readAllBytes(Paths.get("/Users/moonie/Desktop/self.key"));
//        System.out.println(new String(keyBytes));
        System.out.println(new String(keyBytes)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", ""));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(new String(keyBytes)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "")));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey pk = kf.generatePrivate(spec);
        System.out.println(Base64.getEncoder().encodeToString(pk.getEncoded()));


//        JcaPKCS8Generator gen1 = new JcaPKCS8Generator(selfSignedCertificate.key(), null);
//        PemObject obj1 = gen1.generate();
//        StringWriter sw1 = new StringWriter();
//        try (JcaPEMWriter pw = new JcaPEMWriter(sw1)) {
//            pw.writeObject(obj1);
//        }
//        String pkcs8Key1 = sw1.toString();
//        FileOutputStream fos1 = new FileOutputStream("/Users/moonie/Desktop/self.pem");
//        fos1.write(pkcs8Key1.getBytes());
//        fos1.flush();
//        fos1.close();
    }
}
