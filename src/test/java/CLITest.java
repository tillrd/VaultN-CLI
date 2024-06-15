package org.tillrd;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.File;

public class CLITest {
    @BeforeAll
    public static void setup() {
        VaultNAPI.setApiKey("your-valid-api-key");
    }

    @Test
    public void testGenerateCertificate() {
        assertDoesNotThrow(() -> VaultNAPI.generateCertificate());
    }

    @Test
    public void testExportCertificate() {
        assertDoesNotThrow(() -> {
            File certFile = VaultNAPI.exportCertificate("selfsigned", "password", "keystore.jks", "sample.crt");
            assertTrue(certFile.exists());
        });
    }

    @Test
    public void testUploadCertificate() {
        assertDoesNotThrow(() -> {
            File certFile = new File("sample.crt");
            assertTrue(certFile.exists(), "Certificate file should exist for upload test.");
            VaultNAPI.uploadCertificate("sample.crt");
        });
    }

    @Test
    public void testGetConnections() {
        assertDoesNotThrow(() -> {
            String connections = VaultNAPI.getConnections();
            assertNotNull(connections);
        });
    }

    @Test
    public void testListCertificates() {
        assertDoesNotThrow(() -> {
            String certs = VaultNAPI.listCertificates();
            assertNotNull(certs);
        });
    }

    @Test
    public void testDeleteCertificate() {
        assertDoesNotThrow(() -> {
            String certId = "your-cert-id";
            VaultNAPI.deleteCertificate(certId);
        });
    }

    @Test
    public void testValidateCertificate() {
        assertDoesNotThrow(() -> {
            String certId = "your-cert-id";
            VaultNAPI.validateCertificate(certId);
        });
    }

    @Test
    public void testGetCertificateInfo() {
        assertDoesNotThrow(() -> {
            String certId = "your-cert-id";
            String certInfo = VaultNAPI.getCertificateInfo(certId);
            assertNotNull(certInfo);
        });
    }
}