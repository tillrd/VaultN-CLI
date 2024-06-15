package org.tillrd;

import java.io.IOException;
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
            System.out.println("2. Upload Certificate");
            System.out.println("3. Enter API Key");
            System.out.println("4. Get Connections");
            System.out.println("5. List Certificates");
            System.out.println("6. Delete Certificate");
            System.out.println("7. Validate Certificate");
            System.out.println("8. Get Certificate Info");
            System.out.println("9. Help");
            System.out.println("10. Exit");
            System.out.print("\nEnter your choice (1-10): ");
            command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    generateCertificate();
                    break;
                case "2":
                    if (checkApiKey()) {
                        uploadCertificate();
                    }
                    break;
                case "3":
                    enterApiKey();
                    break;
                case "4":
                    if (checkApiKey()) {
                        getConnections();
                    }
                    break;
                case "5":
                    if (checkApiKey()) {
                        listCertificates();
                    }
                    break;
                case "6":
                    if (checkApiKey()) {
                        deleteCertificate();
                    }
                    break;
                case "7":
                    if (checkApiKey()) {
                        validateCertificate();
                    }
                    break;
                case "8":
                    if (checkApiKey()) {
                        getCertificateInfo();
                    }
                    break;
                case "9":
                    displayHelp();
                    break;
                case "10":
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid choice. Type '9' for help.");
            }
        } while (!command.equals("10"));
    }

    private static boolean checkApiKey() {
        if (apiKey.isEmpty()) {
            System.out.println("Please enter your API key first.");
            enterApiKey();
        }
        try {
            if (!VaultNAPI.validateApiKey()) {
                System.out.println("Invalid API key. Please enter a valid API key.");
                enterApiKey();
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error validating API key: " + e.getMessage());
            return false;
        }
        return true;
    }

    private static void generateCertificate() {
        try {
            VaultNAPI.generateCertificate();
            System.out.println("Certificate generated successfully.");
        } catch (Exception e) {
            System.out.println("Error generating certificate: " + e.getMessage());
        }
    }

    private static void uploadCertificate() {
        try {
            VaultNAPI.uploadCertificate();
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
        System.out.println("2. Upload Certificate: Uploads the exported certificate to the VaultN API.");
        System.out.println("3. Enter API Key: Prompts you to enter your VaultN API key.");
        System.out.println("4. Get Connections: Retrieves and displays your VaultN connections using the API key.");
        System.out.println("5. List Certificates: Lists all certificates available in VaultN.");
        System.out.println("6. Delete Certificate: Deletes a specific certificate from VaultN.");
        System.out.println("7. Validate Certificate: Validates a specific certificate using VaultN API.");
        System.out.println("8. Get Certificate Info: Retrieves detailed information about a specific certificate.");
        System.out.println("9. Help: Displays this help message.");
        System.out.println("10. Exit: Exits the application.");
        System.out.println("To use a command, enter the corresponding number from the menu and follow the prompts.");
    }
}