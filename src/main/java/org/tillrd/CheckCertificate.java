package org.tillrd;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class CheckCertificate {
    private static final String KEYSTORE_PATH = "sample.pfx";
    private static final String KEYSTORE_PASSWORD = "password";
    private static final String ALIAS = "vaultn";

    public static void main(String[] args) {
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            try (FileInputStream fis = new FileInputStream(KEYSTORE_PATH)) {
                keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
            }

            if (keyStore.isKeyEntry(ALIAS)) {
                Certificate cert = keyStore.getCertificate(ALIAS);
                System.out.println("Certificate: " + cert.toString());
                System.out.println("Certificate is generated and loaded successfully.");
            } else {
                System.out.println("Certificate with alias '" + ALIAS + "' not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load the certificate.");
        }
    }
}
