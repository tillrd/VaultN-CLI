package org.tillrd;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

public class VaultNAuth {
    private static final String KEYSTORE_PATH = "sample.pfx";
    private static final String KEYSTORE_PASSWORD = "password";
    private static final String ALIAS = "vaultn";
    private static final String USER_GUID = "your_user_guid";
    private static final String VAULT_GUID = "your_vault_guid";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    public static void generateCertificate() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        X500Principal subject = new X500Principal("CN=Test Certificate");
        X509Certificate cert = generateCertificate(subject, keyPair, 365, "SHA256withRSA");

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(null, null);
        keyStore.setKeyEntry(ALIAS, keyPair.getPrivate(), KEYSTORE_PASSWORD.toCharArray(), new Certificate[]{cert});

        try (FileOutputStream fos = new FileOutputStream(KEYSTORE_PATH)) {
            keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
        }
    }

    private static X509Certificate generateCertificate(X500Principal subject, KeyPair keyPair, int days, String algorithm) throws GeneralSecurityException, IOException, OperatorCreationException {
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);

        X500Name issuer = new X500Name(subject.getName());
        X500Name subjectName = new X500Name(subject.getName());
        BigInteger serial = BigInteger.valueOf(now);
        Date endDate = new Date(now + days * 86400000L);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer,
                serial,
                startDate,
                endDate,
                subjectName,
                keyPair.getPublic()
        );

        ContentSigner contentSigner = new JcaContentSignerBuilder(algorithm).build(keyPair.getPrivate());
        return new JcaX509CertificateConverter().getCertificate(certBuilder.build(contentSigner));
    }

    public static String generateToken() throws Exception {
        KeyStore keystore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(KEYSTORE_PATH)) {
            keystore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        }
        RSAPrivateKey privateKey = (RSAPrivateKey) keystore.getKey(ALIAS, KEYSTORE_PASSWORD.toCharArray());

        Algorithm algorithm = Algorithm.RSA256(null, privateKey);
        return JWT.create()
                .withIssuer("Self")
                .withAudience("VAULTN")
                .withClaim("sub", USER_GUID)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000L)) // 1 day expiration
                .sign(algorithm);
    }
}
