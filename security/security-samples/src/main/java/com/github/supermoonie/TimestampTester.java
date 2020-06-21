package com.github.supermoonie;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.Timestamp;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;

/**
 * @author supermoonie
 * @date 2020-06-03
 */
public class TimestampTester {

    public static void main(String[] args) throws CertificateException, NoSuchProviderException, FileNotFoundException {
        Security.addProvider(new BouncyCastleProvider());
        CertificateFactory cf = CertificateFactory.getInstance("X509", "BC");
        // TODO 生成证书
        CertPath certPath = cf.generateCertPath(new FileInputStream("D:\\x.cer"));
        Timestamp timestamp = new Timestamp(new Date(), certPath);
        System.out.println(timestamp.toString());
    }
}
