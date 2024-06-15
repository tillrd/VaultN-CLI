```
\ \    / /        | | | | \ | |       / ____| |    |_   _|
  \ \  / /_ _ _   _| | |_|  \| |______| |    | |      | |  
   \ \/ / _` | | | | | __| . ` |______| |    | |      | |  
    \  / (_| | |_| | | |_| |\  |      | |____| |____ _| |_ 
     \/ \__,_|\__,_|_|\__|_| \_|       \_____|______|_____|
```

## Description

A command-line tool designed as a test field for working with certificates and interacting with the VaultN API.

## Features

- Generate new certificates
- Export certificates to a file
- Upload certificates to the VaultN API
- Manage VaultN API keys
- Retrieve and display VaultN connections
- List, delete, validate, and get information about certificates

## Installation and Setup

### For IntelliJ IDEA Users

#### Clone the Repository

1. Open IntelliJ IDEA.
2. Go to `File > New > Project from Version Control > Git`.
3. Enter the repository URL and clone the project.

#### Build the Project

1. Open the Maven tool window in IntelliJ IDEA (`View > Tool Windows > Maven`).
2. Click on the `Reload All Maven Projects` button to ensure all dependencies are downloaded.
3. Run the `clean` and `install` goals:
   - Right-click on `clean` under `Lifecycle` and select `Run 'clean'`.
   - Right-click on `install` under `Lifecycle` and select `Run 'install'`.

#### Run the Application

1. Navigate to the `CLI.java` file in the `src/main/java/org/tillrd` directory.
2. Right-click on the `CLI.java` file and select `Run 'CLI.main()'`.

### For Non-IntelliJ IDEA Users

#### Prerequisites

Ensure you have the following installed:
- Java JDK 11 or later
- Maven

#### Clone the Repository

```sh
git clone <repository-url>
cd VaultNApp
```

#### Build the Project

```sh
mvn clean install
```

#### Run the Application

```sh
java -cp target/vaultn-cli-1.0-SNAPSHOT.jar org.tillrd.CLI
```

## Usage

After starting the application, you will be presented with a menu. Enter the number corresponding to the action you want to perform:

1. **Generate Certificate**: Generates a new certificate and stores it in a keystore.
2. **Export Certificate**: Exports the generated certificate to a specified file path.
3. **Upload Certificate**: Uploads the exported certificate to the VaultN API.
4. **Enter API Key**: Prompts you to enter your VaultN API key.
5. **Get Connections**: Retrieves and displays your VaultN connections using the API key.
6. **List Certificates**: Lists all certificates available in VaultN.
7. **Delete Certificate**: Deletes a specific certificate from VaultN.
8. **Validate Certificate**: Validates a specific certificate using VaultN API.
9. **Get Certificate Info**: Retrieves detailed information about a specific certificate.
10. **Help**: Displays the help message.
11. **Exit**: Exits the application.

## License

This project is licensed under the MIT License.
