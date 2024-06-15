package org.tillrd;

import java.io.File;
import java.util.Scanner;

public class CLI {
    private static String apiKey = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("\n");
        System.out.println(" \\ \\    / /        | | | | \\ | |       / ____| |    |_   _|");
        System.out.println("  \\ \\  / /_ _ _   _| | |_|  \\| |______| |    | |      | |  ");
        System.out.println("   \\ \\/ / _` | | | | | __| . ` |______| |    | |      | |  ");
        System.out.println("    \\  / (_| | |_| | | |_| |\\  |      | |____| |____ _| |_ ");
        System.out.println("     \\/ \\__,_|\\__,_|_|\\__|_| \\_|       \\_____|______|_____|");
        System.out.println("                                                            ");
        System.out.println("                                                           ");
        System.out.println("VaultN CLI Application");
        System.out.println("Made by Richard Tillard");
        System.out.println("Version 1.0");

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Generate Certificate");
            System.out.println("2. Export Certificate");
            System.out.println("3. Upload Certificate");
            System.out.println("4. Enter API Key");
            System.out.println("5. Get Connections");
            System.out.println("6. List Certificates");
            System.out.println("7. Delete Certificate");
            System.out.println("8. Validate Certificate");
            System.out.println("9. Get Certificate Info");
            System.out.println("10. Help");
            System.out.println("11. Exit");
            System.out.print("\nEnter your choice (1-11): ");
            command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    generateCertificate();
                    break;
                case "2":
                    exportCertificate();
                    break;
                case "3":
                    uploadCertificate();
                    break;
                case "4":
                    enterApiKey();
                    break;
                case "5":
                    getConnections();
                    break;
                case "6":
                    listCertificates();
                    break;
                case "7":
                    deleteCertificate();
                    break;
                case "8":
                    validateCertificate();
                    break;
                case "9":
                    getCertificateInfo();
                    break;
                case "10":
                    displayHelp();
                    break;
                case "11":
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid choice. Type '10' for help.");
            }
        } while (!command.equals("11"));
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
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter alias for the certificate: ");
        String alias = scanner.nextLine().trim();

        System.out.print("Enter password for the keystore: ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter path for the keystore file (e.g., /path/to/keystore.jks): ");
        String keystorePath = scanner.nextLine().trim();

        System.out.print("Enter path to export the certificate to (e.g., /path/to/exported_certificate.crt): ");
        String certPath = scanner.nextLine().trim();

        try {
            File certFile = VaultNAPI.exportCertificate(alias, password, keystorePath, certPath);
            System.out.println("Certificate exported to: " + certFile.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Error exporting certificate: " + e.getMessage());
        }
    }

    private static void uploadCertificate() {
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter path to the certificate file (e.g., /path/to/certificate.crt): ");
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

        System.out.print("Enter your API key: ");
        apiKey = scanner.nextLine().trim();

        VaultNAPI.setApiKey(apiKey);
        System.out.println("API key set.");
    }

    private static void getConnections() {
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }

        try {
            String connections = VaultNAPI.getConnections();
            System.out.println("Connections: " + connections);
        } catch (Exception e) {
            System.out.println("Error getting connections: " + e.getMessage());
        }
    }

    private static void listCertificates() {
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }

        try {
            String certificates = VaultNAPI.listCertificates();
            System.out.println("Certificates: " + certificates);
        } catch (Exception e) {
            System.out.println("Error listing certificates: " + e.getMessage());
        }
    }

    private static void deleteCertificate() {
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter certificate ID to delete: ");
        String certId = scanner.nextLine().trim();

        try {
            VaultNAPI.deleteCertificate(certId);
            System.out.println("Certificate deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting certificate: " + e.getMessage());
        }
    }

    private static void validateCertificate() {
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter certificate ID to validate: ");
        String certId = scanner.nextLine().trim();

        try {
            VaultNAPI.validateCertificate(certId);
            System.out.println("Certificate validated successfully.");
        } catch (Exception e) {
            System.out.println("Error validating certificate: " + e.getMessage());
        }
    }

    private static void getCertificateInfo() {
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter certificate ID to get information: ");
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
        System.out.println("2. Export Certificate: Exports the generated certificate to a specified file path.");
        System.out.println("3. Upload Certificate: Uploads the exported certificate to the VaultN API.");
        System.out.println("4. Enter API Key: Prompts you to enter your VaultN API key.");
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