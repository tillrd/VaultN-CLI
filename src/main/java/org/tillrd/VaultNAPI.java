package org.tillrd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class VaultNAPI {
    private static String apiKey;

    public static void setApiKey(String apiKey) {
        VaultNAPI.apiKey = apiKey;
    }

    public static boolean validateApiKey() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("https://sbx-api.vaultn.com/api/v1/ping");
            request.setHeader("Authorization", "Bearer " + apiKey);

            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 200;
        } finally {
            httpClient.close();
        }
    }

    public static void generateCertificate() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(4096, new SecureRandom());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        X500Name issuer = new X500Name("CN=Test Certificate, L=London, C=GB");
        BigInteger serial = BigInteger.valueOf(new SecureRandom().nextInt());
        Date notBefore = new Date();
        Date notAfter = new Date(notBefore.getTime() + 365L * 24 * 60 * 60 * 1000);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                issuer, serial, notBefore, notAfter, issuer, keyPair.getPublic());

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
        X509Certificate certificate = new JcaX509CertificateConverter()
                .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                .getCertificate(certBuilder.build(signer));

        File keyFile = new File("backend-sample.key");
        try (FileOutputStream fos = new FileOutputStream(keyFile)) {
            fos.write(keyPair.getPrivate().getEncoded());
        }

        File certFile = new File("backend-sample.crt");
        try (FileOutputStream fos = new FileOutputStream(certFile)) {
            fos.write(certificate.getEncoded());
        }

        System.out.println("Certificate and key generated successfully.");
    }

    public static void uploadCertificate() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost uploadFile = new HttpPost("https://sbx-api.vaultn.com/api/v1/upload");
            uploadFile.setHeader("Authorization", "Bearer " + apiKey);

            File certFile = new File("backend-sample.crt");
            FileBody fileBody = new FileBody(certFile);

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addPart("file", fileBody)
                    .build();

            uploadFile.setEntity(entity);

            CloseableHttpResponse response = httpClient.execute(uploadFile);
            HttpEntity responseEntity = response.getEntity();

            System.out.println(EntityUtils.toString(responseEntity));
        } finally {
            httpClient.close();
        }
    }

    public static String getConnections() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("https://sbx-api.vaultn.com/api/v1/connections");
            request.setHeader("Authorization", "Bearer " + apiKey);

            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity());
        } finally {
            httpClient.close();
        }
    }

    public static String listCertificates() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("https://sbx-api.vaultn.com/api/v1/certificates");
            request.setHeader("Authorization", "Bearer " + apiKey);

            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity());
        } finally {
            httpClient.close();
        }
    }

    public static void deleteCertificate(String certId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost("https://sbx-api.vaultn.com/api/v1/certificates/" + certId + "/delete");
            request.setHeader("Authorization", "Bearer " + apiKey);

            HttpResponse response = httpClient.execute(request);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } finally {
            httpClient.close();
        }
    }

    public static void validateCertificate(String certId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost request = new HttpPost("https://sbx-api.vaultn.com/api/v1/certificates/" + certId + "/validate");
            request.setHeader("Authorization", "Bearer " + apiKey);

            HttpResponse response = httpClient.execute(request);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } finally {
            httpClient.close();
        }
    }

    public static String getCertificateInfo(String certId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("https://sbx-api.vaultn.com/api/v1/certificates/" + certId);
            request.setHeader("Authorization", "Bearer " + apiKey);

            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity());
        } finally {
            httpClient.close();
        }
    }

    public static String pingApi() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet("https://sbx-api.vaultn.com/api/v1/ping");
            request.setHeader("Authorization", "Bearer " + apiKey);

            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity());
        } finally {
            httpClient.close();
        }
    }
}