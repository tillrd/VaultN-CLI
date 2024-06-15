package org.tillrd;

import java.io.File;
import java.util.Scanner;

public class CLI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("\nVaultN CLI Application");
        System.out.println("Made by Richard Tillard");
        System.out.println("Version 1.0");

        do {
            System.out.println("\nEnter command (generate-certificate, export-certificate, upload-certificate, enter-api-key, get-connections, list-certificates, delete-certificate, validate-certificate, get-certificate-info, help, exit): ");
            command = scanner.nextLine().trim();

            switch (command) {
                case "generate-certificate":
                    generateCertificate();
                    break;
                case "export-certificate":
                    exportCertificate();
                    break;
                case "upload-certificate":
                    uploadCertificate();
                    break;
                case "enter-api-key":
                    enterApiKey();
                    break;
                case "get-connections":
                    getConnections();
                    break;
                case "list-certificates":
                    listCertificates();
                    break;
                case "delete-certificate":
                    deleteCertificate();
                    break;
                case "validate-certificate":
                    validateCertificate();
                    break;
                case "get-certificate-info":
                    getCertificateInfo();
                    break;
                case "help":
                    displayHelp();
                    break;
                case "exit":
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid command. Type 'help' for a list of commands.");
            }
        } while (!command.equals("exit"));
    }

    private static void generateCertificate() {
        try {
            VaultNAPI.generateCertificate();
            System.out.println("Certificate generated successfully.");
        } catch (Exception e) {
            System.out.println("Error generating certificate: " + e.getMessage());
        }
    }

    private static void exportCertificate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter alias for the certificate: ");
        String alias = scanner.nextLine().trim();

        System.out.println("Enter password for the keystore: ");
        String password = scanner.nextLine().trim();

        System.out.println("Enter path for the keystore file: ");
        String keystorePath = scanner.nextLine().trim();

        System.out.println("Enter path to export the certificate to: ");
        String certPath = scanner.nextLine().trim();

        try {
            File certFile = VaultNAPI.exportCertificate(alias, password, keystorePath, certPath);
            System.out.println("Certificate exported to: " + certFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error exporting certificate: " + e.getMessage());
        }
    }

    private static void uploadCertificate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter path to the certificate file: ");
        String certPath = scanner.nextLine().trim();

        try {
            VaultNAPI.uploadCertificate(certPath);
            System.out.println("Certificate uploaded successfully.");
        } catch (Exception e) {
            System.out.println("Error uploading certificate: " + e.getMessage());
        }
    }

    private static void enterApiKey() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your API key: ");
        String apiKey = scanner.nextLine().trim();

        VaultNAPI.setApiKey(apiKey);
        System.out.println("API key set.");
    }

    private static void getConnections() {
        try {
            String connections = VaultNAPI.getConnections();
            System.out.println("Connections: " + connections);
        } catch (Exception e) {
            System.out.println("Error getting connections: " + e.getMessage());
        }
    }

    private static void listCertificates() {
        try {
            String certificates = VaultNAPI.listCertificates();
            System.out.println("Certificates: " + certificates);
        } catch (Exception e) {
            System.out.println("Error listing certificates: " + e.getMessage());
        }
    }

    private static void deleteCertificate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter certificate ID to delete: ");
        String certId = scanner.nextLine().trim();

        try {
            VaultNAPI.deleteCertificate(certId);
            System.out.println("Certificate deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting certificate: " + e.getMessage());
        }
    }

    private static void validateCertificate() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter certificate ID to validate: ");
        String certId = scanner.nextLine().trim();

        try {
            VaultNAPI.validateCertificate(certId);
            System.out.println("Certificate validated successfully.");
        } catch (Exception e) {
            System.out.println("Error validating certificate: " + e.getMessage());
        }
    }

    private static void getCertificateInfo() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter certificate ID to get information: ");
        String certId = scanner.nextLine().trim();

        try {
            String certInfo = VaultNAPI.getCertificateInfo(certId);
            System.out.println("Certificate Info: " + certInfo);
        } catch (Exception e) {
            System.out.println("Error getting certificate information: " + e.getMessage());
        }
    }

    private static void displayHelp() {
        System.out.println("\nVaultN CLI Help:");
        System.out.println("This application is for testing certificate generation, uploading, and testing the VaultN API implementation.");
        System.out.println("Commands:");
        System.out.println("1. Generate Certificate: Generates a new certificate and stores it in a keystore.");
        System.out.println("2. Enter API Key: Prompts you to enter your VaultN API key.");
        System.out.println("3. Export Certificate: Exports the generated certificate to a specified file path.");
        System.out.println("4. Upload Certificate: Uploads the exported certificate to the VaultN API.");
        System.out.println("5. Get Connections: Retrieves and displays your VaultN connections using the API key.");
        System.out.println("6. List Certificates: Lists all certificates available in VaultN.");
        System.out.println("7. Delete Certificate: Deletes a specific certificate from VaultN.");
        System.out.println("8. Validate Certificate: Validates a specific certificate using VaultN API.");
        System.out.println("9. Get Certificate Info: Retrieves detailed information about a specific certificate.");
        System.out.println("10. Help: Displays this help message.");
        System.out.println("11. Exit: Exits the application.");
        System.out.println("To use a command, enter the corresponding number from the menu and follow the prompts.");
    }
}