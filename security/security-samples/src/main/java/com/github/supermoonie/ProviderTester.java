package com.github.supermoonie;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Provider;
import java.security.Security;

/**
 * Hello world!
 *
 */
public class ProviderTester
{
    public static void main( String[] args )
    {
        Security.addProvider(new BouncyCastleProvider());
        Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            System.out.println(provider);
            provider.forEach((key, value) -> System.out.println("\t" + key));
        }
    }
}
